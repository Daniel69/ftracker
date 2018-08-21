package bid.dbo.ftracker;

import bid.dbo.ftracker.users.gateways.UserIdentityProvider;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.ClientFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.client.Client;
import com.auth0.json.mgmt.client.ClientsPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MainApplication.class)
public class SignUpTest {

    @LocalServerPort
    private int port;

    private WebClient client;

    @Autowired
    private UserIdentityProvider identityProvider;

    @Autowired
    private ManagementAPI api;

    @Before
    public void init(){
        client = WebClient.create("http://localhost:" + port);
    }


    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        final ClientResponse resp = client.post().uri("users/create").contentType(MediaType.APPLICATION_JSON)
            .syncBody("{\n" +
                "    \"email\": \"dbustamante8989890963@gmail.com\",\n" +
                "    \"fullName\":  \"Daniel Bustamante Ospina\",\n" +
                "    \"passwd\": \"rszthwcoA1*\"\n" +
                "}")
            .exchange().block();

        assertThat(resp.statusCode().isError()).isFalse();

        identityProvider.deleteUserIdentity("dbustamante8989890963@gmail.com").block();

    }

    @Test
    public void getApps() throws Auth0Exception {
        ClientFilter filter = new ClientFilter();
        final ClientsPage execute = api.clients().list(filter).execute();
        final List<Client> items = execute.getItems();
    }

}
