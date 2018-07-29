package bid.dbo.ftracker.accounts;

import bid.dbo.ftracker.common.ex.BusinessValidationException;
import reactor.core.publisher.Mono;

import java.util.Date;

import static bid.dbo.ftracker.common.StringUtil.isEmpty;

public interface AccountFactory {

    default Mono<Account> createAccount(String id, String name, String userAccount, String desc, Date date){
        if(!isEmpty(id) && !isEmpty(name) && !isEmpty(userAccount)){
            return Mono.just(Account.builder()
                .active(true)
                .id(id)
                .openDate(date)
                .name(name)
                .userAccount(userAccount)
                .description(desc).build());
        }else {
            return Mono.error(BusinessValidationException.Type.INVALID_ACCOUNT_INITIAL_DATA.build());
        }
    }
}
