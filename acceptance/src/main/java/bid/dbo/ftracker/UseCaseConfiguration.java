package bid.dbo.ftracker;

import bid.dbo.ftracker.accounts.CreateAccountUseCase;
import bid.dbo.ftracker.accounts.QueryAccountsUseCase;
import bid.dbo.ftracker.accounts.gateways.AccountRepository;
import bid.dbo.ftracker.categories.CategoryUseCase;
import bid.dbo.ftracker.categories.gateways.CategoryRepository;
import bid.dbo.ftracker.events.EventBus;
import bid.dbo.ftracker.transactions.CreateTransactionUseCase;
import bid.dbo.ftracker.transactions.gateways.TransactionRepository;
import bid.dbo.ftracker.users.UserAccountsUseCase;
import bid.dbo.ftracker.users.gateways.UserAccountRepository;
import bid.dbo.ftracker.users.gateways.UserIdentityProvider;
import bid.dbo.ftracker.users.gateways.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.sofka.reactive.mapper.ObjectMapper;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public UserAccountsUseCase createUserAccountUseCase(UserAccountRepository userAccounts, UserRepository users, UserIdentityProvider identityProvider, EventBus eventBus) {
        return new UserAccountsUseCase(userAccounts, users, identityProvider, eventBus);
    }

    @Bean
    public CreateAccountUseCase createAccountUseCase(UserAccountRepository userAccounts, AccountRepository accounts){
        return new CreateAccountUseCase(userAccounts, accounts);
    }

    @Bean
    public CreateTransactionUseCase createTransactionUseCase(TransactionRepository transactions, UserAccountRepository users, AccountRepository accounts){
        return new CreateTransactionUseCase(transactions, users, accounts);
    }

    @Bean
    public QueryAccountsUseCase queryAccountsUseCase(AccountRepository accounts, UserAccountRepository userAccounts){
        return new QueryAccountsUseCase(accounts, userAccounts);
    }

    @Bean
    public CategoryUseCase createCategoryUseCase(CategoryRepository categories, ObjectMapper mapper, UserAccountRepository usersAccounts){
        return new CategoryUseCase(categories, usersAccounts, mapper);
    }

}
