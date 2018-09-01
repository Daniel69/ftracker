package bid.dbo.ftracker.identity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    public void shouldCacheToken() {
        final String t1 = tokenProvider.authorizationAPIToken().block();
        final String t2 = tokenProvider.authorizationAPIToken().block();
        final String t3 = tokenProvider.authorizationAPIToken().block();
        assertThat(t1).isEqualTo(t2).isEqualTo(t3);
    }

}
