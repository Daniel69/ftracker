package bid.dbo.ftracker;

import bid.dbo.ftracker.categories.CategoryCommand;
import bid.dbo.ftracker.users.gateways.UserIdentityProvider;
import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.ClientFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.client.Client;
import com.auth0.json.mgmt.client.ClientsPage;
import lombok.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setMaxLengthForSingleLineDescription;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MainApplication.class)
public class CreateCategoryTest {

    @LocalServerPort
    private int port = 8080;

    private WebClient client;

    @Autowired
    private UserIdentityProvider identityProvider;

    @Autowired
    private AuthAPI authAPI;

    @Value("${auth0.apiAudience}")
    private String audience = "https://ftracker.dbo.bid/api";


    @Before
    public void init(){
        client = WebClient.create("http://localhost:" + port);
    }


    @Test
    public void shouldCreateCategory() throws Exception {
        final String password = "rszthwcoA1*";
        final String email = "dbustamante55556@gmail.com";

        //createUserAccount(email, password);

//        final String token = login(email, password);
        final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlFrUkJRelJFT0VJeE1qWXpSRUk1TmtNM056Y3pNek5HT1RNMk5UVXlOelkwUTBRNE1URTBPQSJ9.eyJodHRwczovL2YtdHJhY2tlci5kYm8uYmlkL2VtYWlsIjoiZGJ1c3RhbWFudGU1NTU1NkBnbWFpbC5jb20iLCJpc3MiOiJodHRwczovL2YtdHJhY2tlci5hdXRoMC5jb20vIiwic3ViIjoiYXV0aDB8NWI4NDk4YThkZDQ0YTkyYTI1YTJlOGZmIiwiYXVkIjpbImh0dHBzOi8vZnRyYWNrZXIuZGJvLmJpZC9hcGkiLCJodHRwczovL2YtdHJhY2tlci5hdXRoMC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNTM1NDE2NTUxLCJleHAiOjE1MzU0MjM3NTEsImF6cCI6InFLbUdUbWtVSmY3SjB5ZGZBNGN5Tkk2MFVFS21wbWh3Iiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSJ9.x83QKoYMMSSnkKCvFTRCMONThH_jS5hlNGGg2wcIeM4rNxFA6ogv_AiAUMbcv5ETvVG9z7Q3NS7zOr2r0ApGV12hKfpldLo-aJUdsbm-OPRC5nJd7gmGkqTvIbkP9ckpPELyl8hVa5M9i2eNMp01O63Fp0WDkHZnY8sxQU8vlyFmR5ExNHLArwsL7Z7R177QLBsIRXdm4BoscPPNLFrFhTzszl3kDd_MSSmYBPdYh2_p9Qdalxkn9TSKr_PVeZkxSv4rzL9Bu726saFebiBz5oJG4d5de70PqXZe0lOymWI4cjkdSJTa7qOoWwJhuBikB4WTQ-_cf-AIPkDTYwr3Cw";

        final String account = client.get().uri("users/account/main")
            .header("Authorization", "Bearer "+token).retrieve().bodyToMono(String.class).block();

        CategoryCommand command = new CategoryCommand();
        command.setAccount(account);
        command.setDescription("Test1");
        command.setName("Prueba Categoria2");
        client.post().uri("categories")
            .header("Authorization", "Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON)
            .syncBody(command).retrieve().bodyToMono(Void.class).block();

        final List<Category> categories = client.get().uri("categories/account/{id}", account)
            .header("Authorization", "Bearer "+token)
            .retrieve()
            .bodyToFlux(Category.class).collectList().block();

    }


    private void createUserAccount(String email, String password) throws Auth0Exception {
        final ClientResponse resp = client.post().uri("users/create").contentType(MediaType.APPLICATION_JSON)
            .syncBody("{\n" +
                "    \"email\": \"" + email + "\",\n" +
                "    \"fullName\":  \"Daniel Bustamante Ospina\",\n" +
                "    \"passwd\": \"" + password + "\"\n" +
                "}")
            .exchange().block();

        assertThat(resp.statusCode().isError()).isFalse();
    }

    private String login(String email, String password) throws Auth0Exception {
        return authAPI.login(email, password).setAudience(audience).execute().getAccessToken();
    }

    private void deleteUser(String email){
        identityProvider.deleteUserIdentity("dbustamante8989890963@gmail.com").block();
    }



}
