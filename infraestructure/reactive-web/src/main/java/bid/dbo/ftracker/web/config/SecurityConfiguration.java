package bid.dbo.ftracker.web.config;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;

    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        return JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .csrf().disable()
                .addFilterAt(corsFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT)
                .authorizeExchange()
                .pathMatchers("/users/create").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

    private CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        config.applyPermitDefaultValues();

        config.setAllowCredentials(true);
        config.addExposedHeader("Location");
        config.addAllowedOrigin("*");
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.HEAD.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name()));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

}
