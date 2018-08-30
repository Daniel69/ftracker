package bid.dbo.ftracker.web;

import bid.dbo.ftracker.users.CreateUserAccountCommand;
import bid.dbo.ftracker.users.UserAccountsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import static bid.dbo.ftracker.web.generic.ReactiveIdentityContext.identity;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserAccountServices {

    private final UserAccountsUseCase useCase;

    @PostMapping(path = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> createUserAccount(@RequestBody CreateUserAccountCommand command){
        return useCase.createUserAccount(command);
    }


    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE, path = "account/main")
    public Mono<String> getMainAccount(){
        return identity().flatMap(useCase::getMainAccount);
    }


}
