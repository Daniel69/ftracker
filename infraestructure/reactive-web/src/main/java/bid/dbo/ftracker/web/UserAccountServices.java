package bid.dbo.ftracker.web;

import bid.dbo.ftracker.accounts.CreateAccountCommand;
import bid.dbo.ftracker.accounts.CreateUserAccountUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
public class UserAccountServices {

    @Autowired
    private CreateUserAccountUseCase useCase;


    @PostMapping(path = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> createUserAccount(@RequestBody CreateAccountCommand command){
        return useCase.createUserAccount(command);
    }


}
