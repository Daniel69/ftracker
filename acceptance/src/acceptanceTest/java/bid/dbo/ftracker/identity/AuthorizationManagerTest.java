package bid.dbo.ftracker.identity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationManagerTest {

    @Autowired
    AuthorizationManager authorizationManager;

    @Test
    public void shouldCreateRol(){
        final String id = authorizationManager.createRol("TestRole992", "Test Role").block();
        assertThat(id).isNotBlank();
        authorizationManager.deleteRol(id).block();
        System.out.println(id);
    }

    @Test
    public void shouldListRoles() {
        final List<AuthorizationManager.Role> roles = authorizationManager.getRoles().block();
        assertThat(roles).isNotEmpty();
    }

    @Test
    public void shouldAddRoleToUser() {
        authorizationManager.addRoleToUser("auth0|5b5e161f750f272b7931cd29", "9c275ce9-99e9-4b08-abee-e4a609140bca").block();
    }

    //@Test
    public void souldAddPermitsToRol(){
        //TODO: terminar este test
        authorizationManager.setRolePermisions("", null);
    }
}
