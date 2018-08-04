package bid.dbo.ftracker.web;

import bid.dbo.ftracker.users.CreateUserAccountCommand;
import bid.dbo.ftracker.users.CreateUserAccountUseCase;
import bid.dbo.ftracker.web.generic.ReactiveIdentityContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserAccountServices {

    private final CreateUserAccountUseCase useCase;

    @PostMapping(path = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> createUserAccount(@RequestBody CreateUserAccountCommand command){
        return useCase.createUserAccount(command);
    }

    public Mono<Void> createAccount(){
        return null;
    }

    @GetMapping(path = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> listUsers(){
        return ReactiveIdentityContext.identity().flatMapMany(s -> {
            String authorization = s.getAuthorization();
            return Flux.just("s", "w");
        });
    }


}
