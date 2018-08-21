package bid.dbo.ftracker.identity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class AuthorizationManager {

    private final TokenProvider tokenProvider;
    private final WebClient client;
    private final String applicationId;

    public Mono<String> createRol(String name, String description) {
        return doCall(client.post().uri("/roles")
            .syncBody(new _Role(name, description, "client", applicationId)), _RoleResp.class)
            .map(_RoleResp::get_id);
    }

    public Mono<Void> deleteRol(String roleId) {
        return doCall(client.delete().uri("/roles/{roleId}", roleId), Void.class);
    }

    public Mono<List<Role>> getRoles() {
        return doCall(client.get().uri("/roles"), RolesResp.class).map(RolesResp::getRoles);
    }

    public Mono<Void> addRoleToUser(String userId, String roleId) {
        return doCall(client.patch().uri("/users/{userId}/roles", userId)
            .syncBody(Arrays.asList(roleId)), Void.class);
    }


    public Mono<Void> setRolePermisions(String roleId, List<String> permisions) {
        return doCall(client.get().uri("/roles/{roleId}", roleId), _RoleUpdate.class)
            .map(roleUpdate -> {
                roleUpdate.setApplicationId(applicationId);
                roleUpdate.setApplicationType("client");
                roleUpdate.setPermissions(permisions);
                return roleUpdate;
            }).flatMap(roleUpdate -> doCall(client.put().uri("/roles/{roleId}", roleId)
                .syncBody(roleUpdate), _RoleUpdate.class)).then();
    }

    private <E> Mono<E> doCall(WebClient.RequestHeadersSpec spec, Class<E> clazz) {
        return authAPIToken().flatMap(token ->
            spec.header("Authorization", "Bearer " + token)
                .retrieve().bodyToMono(clazz)
        );
    }

    private Mono<String> authAPIToken() {
        return tokenProvider.authorizationAPIToken();
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class _Role {
        private String name;
        private String description;
        private String applicationType;
        private String applicationId;
    }

    @Data
    private static class _RoleUpdate extends _Role {
        private List<String> permissions;
    }

    @Data
    private static class RolesResp {
        private List<Role> roles;
    }

    @Data
    public static class Role extends _Role {
        private String _id;
        private List<String> permissions;
    }

    @Data
    private static class _RoleResp {
        private String _id;
    }

}
