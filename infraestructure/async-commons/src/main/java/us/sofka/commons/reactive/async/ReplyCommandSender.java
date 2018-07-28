package us.sofka.commons.reactive.async;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

/**
 * Publicador simple de mensajes en RabbitMQ, se asume y delega la disponibilidad en el broker
 * Evolución: "Ser resiliente en la mayor medida posible a particiones de red con el broker"
 */
public abstract class ReplyCommandSender {


    @Autowired
    MessageConfig.BrokerConfig config;

    @Autowired
    MessageConfig.ReactiveReplyRouter router;

    private final ObjectMapper mapper = new ObjectMapper();

    protected <R, C> Mono<R> sendCommand(C command, String commandId, Class<R> type) {
        final String correlationID = UUID.randomUUID().toString().replaceAll("-", "");
        Mono<String> replyHolder = router.register(correlationID);
        channel().send(MessageBuilder.withPayload(command)
                .setHeader("x-reply_id", config.getRoutingKey())
                .setHeader("x-command-id", commandId)
                .setHeader("x-correlation-id", correlationID)
                .build());
        return replyHolder.timeout(Duration.ofSeconds(15)).map(s -> {
            try {
                return String.class.equals(type) ? type.cast(s) : mapper.readValue(s, type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected abstract MessageChannel channel();


}
