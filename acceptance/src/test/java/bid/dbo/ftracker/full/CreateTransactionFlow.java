package bid.dbo.ftracker.full;

import bid.dbo.ftracker.Category;
import bid.dbo.ftracker.categories.CategoryCommand;
import bid.dbo.ftracker.identity.Auth0IdentityProvider;
import bid.dbo.ftracker.users.gateways.UserIdentityProvider;
import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateTransactionFlow {


    private static final String auth0Domain = "f-tracker.auth0.com";
    private static final String clientId = "Vg3wXNP9iIZLUZAz32eRtp4D3w53eQdK";
    private static final String clientSecret = "48YafH-T3zimKGjvOJGdhoYKZwkuEAV7eoyRDeJj-mRdAxu5MxdW8TLBSvQ50b9U";

    private static final String gui_clientId = "qKmGTmkUJf7J0ydfA4cyNI60UEKmpmhw";
    private static final String gui_clientSecret = "8J_ReSYciwmfjDjqYMGAM6b83vba1qsqw7fe8d1lwa99ovcyAMyQYu4DusFjbmUM";

    private static final String apiAudience = "https://f-tracker.auth0.com/api/v2/";
    private static final String appAudience = "https://ftracker.dbo.bid/api";

    private static WebClient client;
    private static UserIdentityProvider identityProvider;
    private static AuthAPI guiAuthApi;

    private String token;
    private String account;

    private String username = "dbustamante"+new Random().nextLong()+"@gmail.com";


    @BeforeClass
    public static void init() throws Auth0Exception {
        client = WebClient.create("http://localhost:8080");
        AuthAPI api = new AuthAPI(auth0Domain, clientId, clientSecret);
        guiAuthApi = new AuthAPI(auth0Domain, gui_clientId, gui_clientSecret);
        AuthRequest authRequest = api.requestToken(apiAudience);
        TokenHolder holder = authRequest.execute();
        ManagementAPI managementApi = new ManagementAPI(auth0Domain, holder.getAccessToken());
        identityProvider = new Auth0IdentityProvider(api, managementApi);
    }


    @Test
    public void createTransactionFlow() throws Exception {
        createUser();

        authenticate();

        account = getMainAccount();

        createCategory();

        final List<Category> categories = getCategories();

        assertThat(categories).hasSize(2);

        System.out.println(categories);



        identityProvider.deleteUserIdentity(username).block();
    }

    private String getMainAccount(){
        return doCall(client.get().uri("users/account/main"), String.class);

    }

    private List<Category> getCategories(){
        return Arrays.asList(doCall(client.get().uri("categories/account/{id}", account), Category[].class));

    }

    private void createCategory(){
        CategoryCommand command = new CategoryCommand();
        command.setAccount(account);
        command.setName("Prueba Categoria2");
        command.setDescription("Test1");
        doCall(client.post().uri("categories")
            .contentType(MediaType.APPLICATION_JSON)
            .syncBody(command), Void.class);
    }

    private void authenticate() throws Auth0Exception {
        token = guiAuthApi.login(username, "rszthwcoA1*")
            .setAudience(appAudience)
            .setScope("profile")
            .execute().getAccessToken();
    }

    private void createUser() throws Exception {
        final ClientResponse resp = client.post().uri("users/create").contentType(MediaType.APPLICATION_JSON)
            .syncBody("{\n" +
                "    \"email\": \""+username +"\",\n" +
                "    \"fullName\":  \"Daniel Bustamante Ospina\",\n" +
                "    \"passwd\": \"rszthwcoA1*\"\n" +
                "}")
            .exchange().block();

        assertThat(resp.statusCode().isError()).isFalse();
    }

    private <E> E doCall(WebClient.RequestHeadersSpec spec, Class<E> clazz) {
        return spec.header("Authorization", "Bearer " + token)
                .retrieve().bodyToMono(clazz).block();
    }

}
