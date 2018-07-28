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
public abstract class EventSender {


    protected <R, C> Mono<Void> send(C command, String commandId, Class<R> type) {
        return Mono.create(s -> {
            try {
                if(channel().send(MessageBuilder.withPayload(command)
                    .setHeader("x-command-id", commandId)
                    .build(), 5000)){
                    s.success();
                }else{
                    s.error(new RuntimeException("Message can't be send!"));
                }
            } catch (Exception e) {
                s.error(e);
            }

        });
    }

    protected abstract MessageChannel channel();


}
