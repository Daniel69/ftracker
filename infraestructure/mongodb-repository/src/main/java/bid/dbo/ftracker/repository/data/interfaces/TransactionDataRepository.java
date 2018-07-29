package bid.dbo.ftracker.repository.data.interfaces;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import bid.dbo.ftracker.repository.data.TransactionData;

public interface TransactionDataRepository extends ReactiveCrudRepository<TransactionData, String> {
}
