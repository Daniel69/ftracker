
package bid.dbo.ftracker.common;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

import static reactor.core.publisher.Mono.fromSupplier;

public interface UniqueIDGenerator {
    static Mono<String> uuid(){
        return fromSupplier(() -> UUID.randomUUID().toString());
    }
    static Flux<String> uuids(){
        return Flux.generate(sink -> sink.next(UUID.randomUUID().toString()));
    }
    static Mono<Date> now(){
        return fromSupplier(Date::new);
    }
}
