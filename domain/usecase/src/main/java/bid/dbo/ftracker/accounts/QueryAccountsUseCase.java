package bid.dbo.ftracker.accounts;

import bid.dbo.ftracker.accounts.gateways.AccountRepository;
import bid.dbo.ftracker.users.User;
import bid.dbo.ftracker.users.UserAccountOperations;
import bid.dbo.ftracker.users.gateways.UserAccountRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@AllArgsConstructor
public class QueryAccountsUseCase implements UserAccountOperations {

    private final AccountRepository accounts;
    private final UserAccountRepository userAccounts;

    public Flux<Account> listAccounts(User user, String userAccountId){
        return userAccounts.findById(userAccountId)
            .flatMap(userAccount -> validateOwnership(userAccount, user))
            .thenMany(accounts.findAllByUserAccount(userAccountId));
    }

}
