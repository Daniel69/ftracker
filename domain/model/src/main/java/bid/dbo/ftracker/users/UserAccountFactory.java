package bid.dbo.ftracker.users;

import bid.dbo.ftracker.common.ex.BusinessValidationException;
import reactor.core.publisher.Mono;
import static bid.dbo.ftracker.common.StringUtil.isEmpty;

import java.util.Collections;
import static java.util.Collections.singletonList;
import java.util.Date;
import java.util.List;

public interface UserAccountFactory {

    default Mono<UserAccount> createUserAccount(String id, Date date, String owner){
        if(!isEmpty(id) && date != null && !isEmpty(owner)){
            return Mono.just(UserAccount.builder().id(id).creationDate(date).owners(singletonList(owner)).build());
        } else {
            return Mono.error(BusinessValidationException.Type.INVALID_USERACCOUNT_INITIAL_DATA.build());
        }
    }

}
