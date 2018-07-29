package bid.dbo.ftracker;

import bid.dbo.ftracker.accounts.CreateUserAccountUseCase;
import bid.dbo.ftracker.users.gateways.UserAccountRepository;
import bid.dbo.ftracker.users.gateways.UserIdentityProvider;
import bid.dbo.ftracker.users.gateways.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateUserAccountUseCase createUserAccountUseCase(UserAccountRepository userAccounts, UserRepository users, UserIdentityProvider identityProvider){
        return new CreateUserAccountUseCase(userAccounts, users, identityProvider);
    }

}
