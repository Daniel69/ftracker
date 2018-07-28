package us.sofka.commons.reactive.async;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.WorkQueueProcessor;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@EnableBinding(Sink.class)
public class ApplicationCommandListener {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ConcurrentHashMap<String, CommandHandler> handlers = new ConcurrentHashMap<>();


    private final WorkQueueProcessor<Message<String>> processor = WorkQueueProcessor.<Message<String>>builder().bufferSize(2048).build();
    private final FluxSink<Message<String>> sink = processor.sink();

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Environment env;

    @Autowired
    ApplicationContext context;

    @StreamListener(Sink.INPUT)
    public void receive(Message<String> msg) throws IOException {
//        List<HashMap> xDeath = (List<HashMap>) msg.getHeaders().get("x-death");
//        Integer count = xDeath != null ? Integer.parseInt(xDeath.get(0).get("count").toString()) : 0;
//        if (count > 4) {
//            Channel channel = msg.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
//            Long tag = msg.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
//            channel.basicAck(tag, false);
//        } else {
        sink.next(msg);
//        }

    }

    @PostConstruct
    public void handleMsj() {
        processor//.publishOn(Schedulers.parallel())//.share()
                .flatMap((Message<String> message) -> {
                    String commandId = message.getHeaders().get("x-command-id", String.class);
                    final CommandHandler handler = handlers.computeIfAbsent(commandId, s -> {
                        String handlerName = env.getProperty(commandId);
                        return context.getBean(handlerName, CommandHandler.class);
                    });
                    try {
                        Object command = mapper.readValue(message.getPayload(), handler.commandClass());
                        Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
                        Long tag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
//                        System.out.println("Proccess Msg");
                        return handler.handle(command).doOnSuccess(o -> {
                            try {
                                if(o != null){
                                    String value = o instanceof String ? (String) o : mapper.writeValueAsString(o);
                                    final String replyID = message.getHeaders().get("x-reply_id", String.class);
                                    final String correlationID = message.getHeaders().get("x-correlation-id", String.class);

                                    rabbitTemplate.convertAndSend("globalReply", replyID, value, msg -> {
                                        msg.getMessageProperties().getHeaders().put("x-correlation-id", correlationID);
                                        return msg;
                                    });
                                }

                                //channel.basicAck(tag, false);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).onErrorResume(throwable -> {
                            final String replyID = message.getHeaders().get("x-reply_id", String.class);
                            final String correlationID = message.getHeaders().get("x-correlation-id", String.class);

                            if(replyID != null && !replyID.isEmpty() && correlationID != null && !correlationID.isEmpty()){
                                rabbitTemplate.convertAndSend("globalReply", replyID, ((Throwable)throwable).getMessage(), msg -> {
                                    msg.getMessageProperties().getHeaders().put("x-correlation-id", correlationID);
                                    msg.getMessageProperties().getHeaders().put("x-exception", "true");
                                    return msg;
                                });
                            }


//                            try {
                            //channel.basicReject(tag, false);
                            return Mono.just("");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                                throw new RuntimeException(e);
//                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, 4096)
                .parallel().runOn(Schedulers.parallel()).subscribe();
    }
}
