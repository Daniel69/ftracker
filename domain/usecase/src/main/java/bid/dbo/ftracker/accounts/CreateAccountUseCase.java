package bid.dbo.ftracker.accounts;

import bid.dbo.ftracker.accounts.gateways.AccountRepository;
import bid.dbo.ftracker.common.UniqueIDGenerator;
import bid.dbo.ftracker.common.ex.ErrorReporter;
import bid.dbo.ftracker.users.*;
import bid.dbo.ftracker.users.gateways.UserAccountRepository;
import bid.dbo.ftracker.users.gateways.UserIdentityProvider;
import bid.dbo.ftracker.users.gateways.UserRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Date;

import static bid.dbo.ftracker.common.ex.BusinessValidationException.Type;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;
import static reactor.core.publisher.Mono.zip;
import static reactor.function.TupleUtils.function;

@AllArgsConstructor
public class CreateAccountUseCase implements UserAccountOperations, AccountFactory, ErrorReporter, UniqueIDGenerator {

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
