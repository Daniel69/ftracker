package bid.dbo.ftracker.event;

import bid.dbo.ftracker.events.Event;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import java.util.concurrent.TimeUnit;

public class InMemoryEventBusTest {

//    WebClient client = WebClient.create("https://us-central1-ftracker-214122.cloudfunctions.net");

    @Test
    public void testSyncConsumer() throws InterruptedException {
        UnicastProcessor<Event> procesor = UnicastProcessor.create();
        procesor.flatMap(event ->
            WebClient.create("https://us-central1-ftracker-214122.cloudfunctions.net")
                .get().uri("/latency?latency=6000&message={msg}", event.getName()).retrieve().bodyToMono(String.class)
        )
            .subscribe(s -> System.out.println(s));

        InMemoryEventBus bus = new InMemoryEventBus(procesor, event -> Mono.empty());
        bus.emit(() -> "event1").block();
        bus.emit(() -> "event2").block();
        bus.emit(() -> "event3").block();

        System.out.println("Fin");
        TimeUnit.SECONDS.sleep(15);
    }

}