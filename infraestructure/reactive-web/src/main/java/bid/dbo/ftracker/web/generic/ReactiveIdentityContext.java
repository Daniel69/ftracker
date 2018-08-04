package bid.dbo.ftracker.web.generic;

import bid.dbo.ftracker.users.User;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

public class ReactiveIdentityContext {

    public static Mono<User> identity(){
        return ReactiveSecurityContextHolder.getContext().map(securityContext -> {
            String principal = securityContext.getAuthentication().getPrincipal().toString();
            DecodedJWT decodedJWT = DecodedJWT.class.cast(securityContext.getAuthentication().getDetails());
            final String email = decodedJWT.getClaim("https://f-tracker.dbo.bid/email").asString();
            return User.builder()
                .authorization(principal.split("\\|")[1])
                .email(email)
                .build();
        });
    }
}
