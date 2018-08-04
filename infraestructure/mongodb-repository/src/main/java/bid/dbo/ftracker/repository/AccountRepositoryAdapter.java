package bid.dbo.ftracker.repository;

import bid.dbo.ftracker.accounts.Account;
import bid.dbo.ftracker.accounts.gateways.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import us.sofka.reactive.mapper.ObjectMapper;
import bid.dbo.ftracker.repository.data.AccountData;
import bid.dbo.ftracker.repository.data.interfaces.AccountDataRepository;
import bid.dbo.ftracker.repository.generic.AdapterOperations;

@Repository
public class AccountRepositoryAdapter extends AdapterOperations<Account, AccountData, String, AccountDataRepository> implements AccountRepository {

    @Autowired
    public AccountRepositoryAdapter(AccountDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Account.AccountBuilder.class).build());
    }

    @Override
    public Flux<Account> findAllByUserAccount(String userAccount) {
        return doQueryMany(repository.findAll(Example.of(new AccountData(userAccount))));
    }
}
