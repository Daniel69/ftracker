package bid.dbo.ftracker.users.gateways;

import reactor.core.publisher.Mono;

public interface UserIdentityProvider {
    Mono<String> createUserIdentity(String email, String password);
    Mono<Void> deleteUserIdentity(String email);
}
