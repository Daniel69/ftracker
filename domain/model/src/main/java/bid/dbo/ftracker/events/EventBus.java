package bid.dbo.ftracker.events;

import reactor.core.publisher.Mono;

public interface EventBus {
    Mono<Void> emit(Event event);
}
