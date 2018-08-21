package bid.dbo.ftracker.identity;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class TokenProvider {

    private final Mono<String> authorizationAPIToken;

    public Mono<String> authorizationAPIToken(){
        return authorizationAPIToken;
    }

}
