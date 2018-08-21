package bid.dbo.ftracker.repository.adapters;

import bid.dbo.ftracker.users.UserAccount;
import bid.dbo.ftracker.users.gateways.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import us.sofka.reactive.mapper.ObjectMapper;
import bid.dbo.ftracker.repository.data.UserAccountData;
import bid.dbo.ftracker.repository.data.interfaces.UserAccountDataRepository;
import bid.dbo.ftracker.repository.generic.AdapterOperations;

@Repository
public class UserAccountRepositoryAdapter extends AdapterOperations<UserAccount, UserAccountData, String, UserAccountDataRepository> implements UserAccountRepository {

    @Autowired
    public UserAccountRepositoryAdapter(UserAccountDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, data -> mapper.mapBuilder(data, UserAccount.UserAccountBuilder.class).build());
    }
}
