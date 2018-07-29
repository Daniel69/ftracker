package bid.dbo.ftracker.accounts;

import bid.dbo.ftracker.common.UniqueIDGenerator;
import bid.dbo.ftracker.common.ex.ErrorReporter;
import bid.dbo.ftracker.users.User;
import bid.dbo.ftracker.users.UserAccount;
import bid.dbo.ftracker.users.UserAccountFactory;
import bid.dbo.ftracker.users.UserFactory;
import bid.dbo.ftracker.users.gateways.UserAccountRepository;
import bid.dbo.ftracker.users.gateways.UserIdentityProvider;
import bid.dbo.ftracker.users.gateways.UserRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Date;

import static bid.dbo.ftracker.common.ex.BusinessValidationException.Type;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.zip;
import static reactor.function.TupleUtils.function;

@AllArgsConstructor
public class CreateUserAccountUseCase implements UserAccountFactory, UserFactory, ErrorReporter, UniqueIDGenerator {

    private final UserAccountRepository userAccounts;
    private final UserRepository users;
    private final UserIdentityProvider identities;

    public Mono<Void> createUserAccount(CreateAccountCommand command) {
        return noDuplicateUser(command.getEmail()).then(zip(now(), uuid()))
            .flatMap(function((now, id) -> {
                final Mono<User> user = createNewUser(command.getEmail(), command.getFullName(), now, command.getPasswd());
                final Mono<UserAccount> userAccount = createUserAccount(id, now, command.getEmail());
                return zip(user, userAccount);
        })).flatMap(function(this::save));
    }

    private Mono<User> createNewUser(String email, String fullName, Date now, String passwd) {
        return identities.createUserIdentity(email, passwd).flatMap(id -> createUser(email, fullName, now, id));
    }

    private Mono<Void> save(User user, UserAccount userAccount){
        return zip(users.save(user), userAccounts.save(userAccount)).then();
    }

    private Mono<Void> noDuplicateUser(String email){
        return users.findById(email).hasElement().flatMap(exist -> exist ? dError(Type.USER_ALREADY_EXIST) : empty());
    }
}
