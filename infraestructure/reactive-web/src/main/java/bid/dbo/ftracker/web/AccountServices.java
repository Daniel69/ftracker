package bid.dbo.ftracker.web;

import bid.dbo.ftracker.accounts.Account;
import bid.dbo.ftracker.accounts.CreateAccountCommand;
import bid.dbo.ftracker.accounts.CreateAccountUseCase;
import bid.dbo.ftracker.accounts.QueryAccountsUseCase;
import bid.dbo.ftracker.transactions.CreateTransactionCommand;
import bid.dbo.ftracker.transactions.CreateTransactionUseCase;
import bid.dbo.ftracker.transactions.QueryTransactionsUseCase;
import bid.dbo.ftracker.transactions.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static bid.dbo.ftracker.web.generic.ReactiveIdentityContext.identity;

@RestController
@AllArgsConstructor
@RequestMapping("accounts")
public class AccountServices {


    private final CreateAccountUseCase useCase;
    private final CreateTransactionUseCase createTransactions;
    private final QueryAccountsUseCase queryAccountsUseCase;
    private final QueryTransactionsUseCase queryTransactionsUseCase;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> createAccount(@RequestBody CreateAccountCommand command) {
        return useCase.createAccount(command, identity()).map(Account::getId);
    }

    @GetMapping(path = "/byUserAccount/{userAccount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Account> listAccounts(@PathVariable("userAccount") String userAccount) {
        return identity().flatMapMany(user -> queryAccountsUseCase.listAccounts(user, userAccount));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{account}/transactions")
    public Mono<String> createTransaction(@RequestBody CreateTransactionCommand command, @PathVariable("account") String account){
        return identity().map(user -> command.toBuilder().user(user).account(account).build())
            .flatMap(createTransactions::createTransaction).map(Transaction::getId);
    }

    @GetMapping(path = "/{account}/transactions")
    public Mono<List<Transaction>> findTransactions(@PathVariable("account") String account){
        return identity().flatMap(user -> queryTransactionsUseCase.findTransactions(user, account));
    }

}