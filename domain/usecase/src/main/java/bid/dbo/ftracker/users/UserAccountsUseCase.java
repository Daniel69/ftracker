package bid.dbo.ftracker.users;

import bid.dbo.ftracker.common.ex.ErrorReporter;
import bid.dbo.ftracker.events.EventBus;
import bid.dbo.ftracker.events.UserAccountCreated;
import bid.dbo.ftracker.users.gateways.UserAccountRepository;
import bid.dbo.ftracker.users.gateways.UserIdentityProvider;
import bid.dbo.ftracker.users.gateways.UserRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import static bid.dbo.ftracker.common.UniqueIDGenerator.now;
import static bid.dbo.ftracker.common.UniqueIDGenerator.uuid;
import static bid.dbo.ftracker.common.ex.BusinessValidationException.Type;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.zip;
import static reactor.function.TupleUtils.function;

@AllArgsConstructor
public class UserAccountsUseCase implements UserAccountFactory, UserFactory, ErrorReporter {

    private final UserAccountRepository userAccounts;
    private final UserRepository users;
    private final UserIdentityProvider identities;
    private final EventBus events;

    public Mono<Void> createUserAccount(CreateUserAccountCommand command) {
        return noDuplicateUser(command.getEmail()).then(zip(now(), uuid()))
            .flatMap(function((now, id) -> {
                final Mono<User> user = createUser(command.getEmail(), command.getFullName(), now, id);
                final Mono<UserAccount> userAccount = createUserAccount(id, now, command.getEmail());
                return identities.createUserIdentity(command.getEmail(), command.getPasswd())
                    .then(zip(user, userAccount));
        })).flatMap(function(this::save));
    }

    public Mono<String> getMainAccount(User user){
        return users.findById(user.getEmail()).map(User::getMainAccount);
    }

    private Mono<Void> save(User user, UserAccount userAccount){
        return zip(
            users.save(user),
            userAccounts.save(userAccount)
        ).flatMap(objects -> {
            System.out.println(objects);
            return events.emit(new UserAccountCreated(userAccount.getId()));
        });
    }

    private Mono<Void> noDuplicateUser(String email){
        return users.findById(email).hasElement().flatMap(exist -> exist ? dError(Type.USER_ALREADY_EXIST) : empty());
    }

}
