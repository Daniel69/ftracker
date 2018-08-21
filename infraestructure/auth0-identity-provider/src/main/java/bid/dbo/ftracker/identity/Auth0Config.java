package bid.dbo.ftracker.identity;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Configuration
public class Auth0Config {

    @Value("${auth0.domain}")
    private String auth0Domain;

    @Value("${auth0.clientId}")
    private String clientId;

    @Value("${auth0.authorization.appId}")
    private String applicationId;

    @Value("${auth0.clientSecret}")
    private String clientSecret;

    @Value("${auth0.managementApiAudience}")
    private String apiAudience;

    @Value("${auth0.authorizationApiAudience}")
    private String authApiAudience;

    @Value("${auth0.authorizationApiExp}")
    private Integer authApiExp;

    @Value("${auth0.authorization.baseUrl}")
    private String authBaseUrlAPI;

    @Bean
    public AuthAPI authAPI(){
        return new AuthAPI(auth0Domain, clientId, clientSecret);
    }

    @Bean
    public ManagementAPI managementAPI(AuthAPI authAPI) throws Auth0Exception {
        AuthRequest authRequest = authAPI.requestToken(apiAudience);
        TokenHolder holder = authRequest.execute();
        return new ManagementAPI(auth0Domain, holder.getAccessToken());
    }

    @Bean
    public TokenProvider tokenProvider(AuthAPI authAPI) {
        final Mono<String> token = Mono.defer(() -> {
            System.out.println("Request for Authorization API Token");
            final AuthRequest authRequest = authAPI.requestToken(authApiAudience).setScope("create:roles");
            try {
                return Mono.just(authRequest.execute().getAccessToken());
            } catch (Auth0Exception e) {
                return Mono.error(e);
            }
        }).cache(Duration.ofSeconds(authApiExp));

        return new TokenProvider(token);
    }

    @Bean
    public AuthorizationManager authorizationManager(TokenProvider tokenProvider){
        final WebClient client = WebClient.create(authBaseUrlAPI);
        return new AuthorizationManager(tokenProvider, client, applicationId);
    }

}
