package bid.dbo.ftracker.repository.adapters;

import bid.dbo.ftracker.repository.data.UserData;
import bid.dbo.ftracker.repository.data.interfaces.UserDataRepository;
import bid.dbo.ftracker.users.User;
import bid.dbo.ftracker.users.gateways.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import us.sofka.reactive.mapper.ObjectMapper;
import bid.dbo.ftracker.repository.generic.AdapterOperations;

@Repository
public class UserRepositoryAdapter extends AdapterOperations<User, UserData, String, UserDataRepository> implements UserRepository {

    @Autowired
    public UserRepositoryAdapter(UserDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, data -> mapper.mapBuilder(data, User.UserBuilder.class).build());
    }

    @Override
    public Flux<User> findAll() {
        return doQueryMany(repository.findAll());
    }
}
