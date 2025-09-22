package ba.unsa.etf.cehajic.hcehajic2.appback.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
public class SecurityConfigJwt {

    @Value("${app.jwt.secret}")   // base64 string iz application.properties
    private String secret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/manager/login", "/api/v1/manager/create", "/api/v1/token/mobile").permitAll()
                        .requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER")
                        .requestMatchers("/api/v1/child/**").hasAnyRole("MANAGER","WORKER")
                        .requestMatchers("api/v1/task/**").hasAnyRole("MANAGER","WORKER")
                        .requestMatchers("/api/v1/account/**").hasAnyRole("MANAGER","WORKER")
                        .requestMatchers("/api/v1/token/**").hasAnyRole("MANAGER","WORKER")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()) // mapiraj claim → authorities
                ));
        return http.build();
    }

    // HS256 decoder (SECRET je base64!)
    @Bean
    JwtDecoder jwtDecoder() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);           // dekodiraj base64
        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(jwt -> {
            var roles = Optional.ofNullable(jwt.getClaimAsStringList("roles"))
                    .orElse(List.of());
            // ↓ Cast elementa na GrantedAuthority
            return roles.stream()
                    .map(r -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + r))
                    .collect(Collectors.toList()); // List<GrantedAuthority>
        });
        return conv;
    }

}
