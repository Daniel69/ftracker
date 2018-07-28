
package us.sofka.reactive.common;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

import static reactor.core.publisher.Mono.fromSupplier;

public interface UniqueIDGenerator {


    default Mono<String> uuid(){
        return fromSupplier(() -> UUID.randomUUID().toString());
    }

    default Flux<String> uuids(){
        return Flux.generate(sink -> sink.next(UUID.randomUUID().toString()));
    }

    default Mono<Date> now(){
        return fromSupplier(Date::new);
    }
}
