package bid.dbo.ftracker.repository.data.interfaces;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import bid.dbo.ftracker.repository.data.AccountData;

public interface AccountDataRepository extends ReactiveCrudRepository<AccountData, String>, ReactiveQueryByExampleExecutor<AccountData> {
}
