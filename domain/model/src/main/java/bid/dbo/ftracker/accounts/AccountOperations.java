package bid.dbo.ftracker.accounts;

import bid.dbo.ftracker.common.ex.BusinessValidationException;
import bid.dbo.ftracker.common.ex.ErrorReporter;
import bid.dbo.ftracker.users.UserAccount;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.just;

public interface AccountOperations extends ErrorReporter {

    default Mono<Account> validateOwner(Account account, UserAccount userAccount){
        return account.getUserAccount().equals(userAccount.getId()) ? just(account) :
            dError(BusinessValidationException.Type.INVALID_OPERATION_INTENT);
    }
}
