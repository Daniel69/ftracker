package bid.dbo.ftracker.common;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseRepository<Entity, Key> {
    Mono<Entity> findById(Key key);
    Flux<Entity> findByExample(Entity entity);
    Mono<Entity> save(Entity entity);
}
