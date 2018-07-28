package us.sofka.commons.reactive.async;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;
import reactor.util.concurrent.Queues;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Import(ApplicationCommandListener.class)
public class MessageConfig {

    @Value("${app.async.reply.prefix}")
    private String prefix;

    @Bean
    public Queue replyQueue() {
        final String replyPrefix = prefix != null && !prefix.isEmpty() ? prefix : "global-reply-";
        return new AnonymousQueue(new AnonymousQueue.Base64UrlNamingStrategy(replyPrefix));
    }

    @Bean
    public Exchange globalReplyExchange() {
        return new TopicExchange("globalReply", true, false);
    }


    @Bean
    public Binding replyBinding(BrokerConfig config) {
        System.out.println("Configurando Bindig");
        return BindingBuilder.bind(replyQueue()).to(globalReplyExchange()).with(config.getRoutingKey()).noargs();
    }

    @Bean
    public BrokerConfig brokerConfig(){
        return new BrokerConfig();
    }

    @Bean
    public Listener msgListener(ReactiveReplyRouter router){
        return new Listener(router);
    }

    @Bean
    public ReactiveReplyRouter router(){
        return new ReactiveReplyRouter();
    }

    public static class Listener {

        private final ReactiveReplyRouter router;

        private Listener(ReactiveReplyRouter router) {
            this.router = router;
        }

        @RabbitListener(queues="#{replyQueue}", concurrency = "5")
        public void receive(Message<String> message) {
            String correlationID = message.getHeaders().get("x-correlation-id", String.class);
            if(message.getHeaders().containsKey("x-exception")){
                router.routeError(correlationID, message.getPayload());
            }else{
                router.routeReply(correlationID, message.getPayload());
            }
        }
    }

    public static class BrokerConfig{
        private final String routingKey = UUID.randomUUID().toString().replaceAll("-", "");
        public String getRoutingKey() {
            return routingKey;
        }
    }

    public static class ReactiveReplyRouter {

        private final ConcurrentHashMap<String, UnicastProcessor<String>> processors = new ConcurrentHashMap<>();

        public Mono<String> register(String correlationID){
            final UnicastProcessor<String> processor = UnicastProcessor.create(Queues.<String>one().get());
            processors.put(correlationID, processor);
            return processor.singleOrEmpty();
        }

        public void routeReply(String correlationID, String data){
            final UnicastProcessor<String> processor = processors.remove(correlationID);
            if(processor != null){
                processor.onNext(data);
                processor.onComplete();
            }
        }

        public <E> void routeError(String correlationID, String data){
            final UnicastProcessor<String> processor = processors.remove(correlationID);
            if(processor != null){
                processor.onError(new RuntimeException(data));
            }
        }

    }

}
