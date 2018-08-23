package bid.dbo.ftracker.accounts;

import bid.dbo.ftracker.accounts.gateways.AccountRepository;
import bid.dbo.ftracker.common.ex.ErrorReporter;
import bid.dbo.ftracker.users.User;
import bid.dbo.ftracker.users.UserAccount;
import bid.dbo.ftracker.users.UserAccountOperations;
import bid.dbo.ftracker.users.gateways.UserAccountRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import static bid.dbo.ftracker.common.UniqueIDGenerator.now;
import static bid.dbo.ftracker.common.UniqueIDGenerator.uuid;
import static reactor.core.publisher.Mono.zip;
import static reactor.function.TupleUtils.function;

@AllArgsConstructor
public class CreateAccountUseCase implements UserAccountOperations, AccountFactory, ErrorReporter {

    private final UserAccountRepository userAccounts;
    private final AccountRepository accounts;

    public Mono<Account> createAccount(CreateAccountCommand command, Mono<User> mUser) {
        return validateUserAccount(command.getUserAccount(), mUser)
            .then(zip(now(), uuid()))
            .flatMap(function((now, id) ->
                createAccount(id, command.getName(), command.getUserAccount(), command.getDescription(), now)
            )).flatMap(accounts::save);
    }

    private Mono<UserAccount> validateUserAccount(String userAccountId, Mono<User> identity) {
        return userAccounts.findById(userAccountId)
            .zipWith(identity)
            .flatMap(function(this::validateOwnership));
    }

}
