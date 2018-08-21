package bid.dbo.ftracker.identity;

import bid.dbo.ftracker.users.gateways.UserIdentityProvider;
import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Component
@AllArgsConstructor
public class Auth0IdentityProvider implements UserIdentityProvider {

    private final AuthAPI api;
    private final ManagementAPI managementAPI;

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

    @Override
    public Mono<Void> deleteUserIdentity(String email) {
        return Flux.defer(() -> {
            final Request<List<User>> rqt = managementAPI.users().listByEmail(email, new FieldsFilter().withFields("user_id,email", true));
            try {
                return Flux.fromIterable(rqt.execute());
            }catch (Auth0Exception e){
                return Flux.error(e);
            }
        }).flatMap(user -> {
            try {
                return Mono.justOrEmpty(managementAPI.users().delete(user.getId()).execute());
            } catch (Auth0Exception e) {
                return Mono.error(e);
            }
        }).then();
    }
}
