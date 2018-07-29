package bid.dbo.ftracker.identity;

import bid.dbo.ftracker.users.gateways.UserIdentityProvider;
import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class Auth0IdentityProvider implements UserIdentityProvider {

    private final AuthAPI api;

    @Autowired
    public Auth0IdentityProvider(AuthAPI api) {
        this.api = api;
    }

    @Override
    public Mono<String> createUserIdentity(String email, String password) {
        return Mono.defer(() -> {
            try {
                CreatedUser user = api.signUp(email, password, "Username-Password-Authentication").execute();
                return Mono.just(user.getUserId());
            } catch (Auth0Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.elastic());
    }
}
