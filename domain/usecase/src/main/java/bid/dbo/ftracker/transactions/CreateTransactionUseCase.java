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
import reactor.core.publisher.Mono;

import static bid.dbo.ftracker.common.UniqueIDGenerator.now;
import static bid.dbo.ftracker.common.UniqueIDGenerator.uuid;
import static reactor.core.publisher.Mono.zip;
import static reactor.function.TupleUtils.function;

@AllArgsConstructor
public class CreateTransactionUseCase implements TransactionFactory, AccountOperations, UserAccountOperations {

    private final TransactionRepository transactions;
    private final UserAccountRepository userAccounts;
    private final AccountRepository accounts;

    public Mono<Transaction> createTransaction(CreateTransactionCommand command) {
        final Mono<UserAccount> userAccountM = userAccounts.findById(command.getUserAccount());
        final Mono<Account> accountM = accounts.findById(command.getAccount());

        return create(command.getAccount(), command.getAmount(), command.getMetaData(), command.getCategoryId())
            .flatMap(transaction -> zip(userAccountM, accountM)
                .flatMap(tpl -> validateAccess(command.getUser(), tpl.getT1(), tpl.getT2()))
                .then(saveNew(transaction))
            );
    }

    private Mono<?> validateAccess(User user, UserAccount userAccount, Account account) {
        return zip(validateOwnership(userAccount, user), validateOwner(account, userAccount));
    }

    private Mono<Transaction> saveNew(Transaction transaction) {
        return zip(uuid(), now()).map(function(prepareNew(transaction))).flatMap(transactions::save);
    }


}
