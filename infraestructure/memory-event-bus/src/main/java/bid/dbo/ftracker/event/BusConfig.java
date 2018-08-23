package bid.dbo.ftracker.event;

import bid.dbo.ftracker.categories.CategoryUseCase;
import bid.dbo.ftracker.events.Event;
import bid.dbo.ftracker.events.UserAccountCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BusConfig {


    private final UnicastProcessor<Event> procesor = UnicastProcessor.create();
    private final Map<String, EventHandler> handlers = new HashMap<>();

    @Autowired
    private CategoryUseCase categoryUseCase;

    @Bean
    public InMemoryEventBus inMemoryEventBus(EventHandler handler){
        return new InMemoryEventBus(procesor, handler);
    }

    @Bean
    public EventHandler handler(){
        handlers.put(UserAccountCreated.NAME, event -> categoryUseCase.createGeneralCategory((UserAccountCreated) event));
        return event -> handlers.containsKey(event.getName()) ? handlers.get(event.getName()).handle(event) : Mono.error(new RuntimeException("No Hanlder for Event: " + event.getName()));
    }

}
