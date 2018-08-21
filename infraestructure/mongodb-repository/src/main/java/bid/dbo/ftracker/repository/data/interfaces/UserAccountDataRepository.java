package bid.dbo.ftracker.repository.data.interfaces;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import bid.dbo.ftracker.repository.data.UserAccountData;

public interface UserAccountDataRepository extends ReactiveCrudRepository<UserAccountData, String>, ReactiveQueryByExampleExecutor<UserAccountData> {
}
