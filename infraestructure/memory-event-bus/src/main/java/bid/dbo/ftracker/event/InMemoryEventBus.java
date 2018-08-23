package bid.dbo.ftracker.event;

import bid.dbo.ftracker.events.Event;
import bid.dbo.ftracker.events.EventBus;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;


public class InMemoryEventBus implements EventBus {

    private final UnicastProcessor<Event> processor;
    private final EventHandler handler;

    public InMemoryEventBus(UnicastProcessor<Event> processor, EventHandler handler) {
        this.processor = processor;
        this.handler = handler;

        processor.flatMap(handler::handle).onErrorResume(e -> {
            e.printStackTrace();
            return Mono.empty();
        }).subscribe();
    }

    @Override
    public Mono<Void> emit(Event event) {
        return Mono.defer(() -> {
            processor.onNext(event);
            return Mono.empty();
        });
    }
}
