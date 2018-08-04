package bid.dbo.ftracker.accounts.gateways;

import bid.dbo.ftracker.accounts.Account;
import bid.dbo.ftracker.common.BaseRepository;
import reactor.core.publisher.Flux;

public interface AccountRepository extends BaseRepository<Account, String> {

    Flux<Account> findAllByUserAccount(String userAccount);

}
