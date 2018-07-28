package us.sofka.reactive.repository.data.interfaces;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import us.sofka.reactive.repository.data.FoundAlertData;

public interface FoundAlertDataRepository extends ReactiveCrudRepository<FoundAlertData, String> {
}
