package us.sofka.reactive.alerts.repository;

import reactor.core.publisher.Mono;
import us.sofka.reactive.alerts.model.events.FoundAlertEvent;

public interface FoundAlertEventRepository {

    Mono<FoundAlertEvent> save(FoundAlertEvent event);
}
