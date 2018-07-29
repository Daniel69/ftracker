package bid.dbo.ftracker.common;

import reactor.core.publisher.Mono;

public interface BaseRepository<Entity, Key> {
    Mono<Entity> findById(Key key);
    Mono<Entity> save(Entity entity);
}
