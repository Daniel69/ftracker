package bid.dbo.ftracker.users;

import bid.dbo.ftracker.common.ex.BusinessValidationException;
import reactor.core.publisher.Mono;
import static bid.dbo.ftracker.common.StringUtil.isEmpty;

import java.util.Date;

public interface UserFactory {

    default Mono<User> createUser(String email, String fullName, Date date, String auth){
        if(!isEmpty(email) && date != null && !isEmpty(auth)){
            return Mono.just(User.builder().email(email).authorization(auth).fullName(fullName).singUpDate(date).build());
        }else{
            return Mono.error(BusinessValidationException.Type.INVALID_USER_INITIAL_DATA.build());
        }

    }
}
