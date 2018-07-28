package us.sofka.reactive.repository.data.interfaces;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import us.sofka.reactive.repository.data.FoundAlertEventData;

public interface FoundAlertEventDataRepository extends ReactiveCrudRepository<FoundAlertEventData, String> {
}
