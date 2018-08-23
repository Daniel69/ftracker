package bid.dbo.ftracker.event;

import bid.dbo.ftracker.events.Event;
import reactor.core.publisher.Mono;

public interface EventHandler {

    Mono<Void> handle(Event event);

}
