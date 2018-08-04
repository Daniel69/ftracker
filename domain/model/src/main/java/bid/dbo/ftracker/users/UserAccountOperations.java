package bid.dbo.ftracker.users;

import bid.dbo.ftracker.common.ex.BusinessValidationException;
import bid.dbo.ftracker.common.ex.ErrorReporter;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.just;

public interface UserAccountOperations extends ErrorReporter {

    default Mono<UserAccount> validateOwnership(UserAccount account, User user){
        return account.getOwners().contains(user.getEmail()) ? just(account) :
            dError(BusinessValidationException.Type.INVALID_OPERATION_INTENT);
    }
}
