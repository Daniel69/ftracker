package bid.dbo.ftracker.users.gateways;

import bid.dbo.ftracker.common.BaseRepository;
import bid.dbo.ftracker.users.User;
import reactor.core.publisher.Flux;

public interface UserRepository extends BaseRepository<User, String> {
    Flux<User> findAll();
}
