package bid.dbo.ftracker.transactions;

import bid.dbo.ftracker.accounts.Account;
import bid.dbo.ftracker.accounts.AccountOperations;
import bid.dbo.ftracker.accounts.gateways.AccountRepository;
import bid.dbo.ftracker.transactions.gateways.TransactionRepository;
import bid.dbo.ftracker.users.User;
import bid.dbo.ftracker.users.UserAccount;
import bid.dbo.ftracker.users.UserAccountOperations;
import bid.dbo.ftracker.users.gateways.UserAccountRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

import static bid.dbo.ftracker.common.UniqueIDGenerator.now;
import static bid.dbo.ftracker.common.UniqueIDGenerator.uuid;
import static reactor.core.publisher.Mono.zip;
import static reactor.function.TupleUtils.function;

@AllArgsConstructor
public class QueryTransactionsUseCase implements AccountOperations, UserAccountOperations {

    private final TransactionRepository transactions;
    private final UserAccountRepository userAccounts;
    private final AccountRepository accounts;


    public Mono<List<Transaction>> findTransactions(User user, String account) {
        final Flux<Transaction> transactions = this.transactions.findByExample(Transaction.builder().account(account).build());
        return accounts.findById(account)
            .flatMap(account1 -> userAccounts.findById(account1.getUserAccount())
            .flatMap(userAccount -> validateAccess(user, userAccount, account1)))
            .zipWith(transactions.collectList()).map(Tuple2::getT2);
    }

    private Mono<Tuple2<UserAccount, Account>> validateAccess(User user, UserAccount userAccount, Account account) {
        return zip(validateOwnership(userAccount, user), validateOwner(account, userAccount));
    }


}
