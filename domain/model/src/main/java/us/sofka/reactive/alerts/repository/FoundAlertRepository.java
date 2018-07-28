package us.sofka.reactive.alerts.repository;

import reactor.core.publisher.Mono;
import us.sofka.reactive.alerts.model.FoundAlert;

public interface FoundAlertRepository {

    Mono<FoundAlert> save(FoundAlert alert);

    Mono<FoundAlert> findById(String alertId);
}
