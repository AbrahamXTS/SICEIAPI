package mx.uady.sicei.kardex_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  @Bean
  SecurityFilterChain configureSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .authorizeHttpRequests(
            requests -> {
              requests.requestMatchers("/actuator/**").permitAll();
              requests
                  .requestMatchers("/docs", "/webjars/**", "/swagger-ui/**", "/v3/api-docs/**")
                  .permitAll();

              requests.anyRequest().authenticated();
            })
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .oauth2ResourceServer(
            resourceServer ->
                resourceServer.jwt(
                    jwtConfigurer ->
                        jwtConfigurer.jwtAuthenticationConverter(
                            jwtToken -> getPermissionsConverter().convert(jwtToken))))
        .build();
  }

  private JwtAuthenticationConverter getPermissionsConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("PERMISSION:");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

    return jwtAuthenticationConverter;
  }

  private void secureAPI(
      AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry
          requests,
      String path,
      String entity) {
    requests.requestMatchers(HttpMethod.OPTIONS, path).permitAll();
    requests
        .requestMatchers(HttpMethod.GET, path)
        .hasAuthority(String.format("PERMISSION:%s:read", entity));
    requests
        .requestMatchers(HttpMethod.POST, path)
        .hasAuthority(String.format("PERMISSION:%s:create", entity));
    requests
        .requestMatchers(HttpMethod.PUT, path)
        .hasAuthority(String.format("PERMISSION:%s:update", entity));
    requests
        .requestMatchers(HttpMethod.PATCH, path)
        .hasAuthority(String.format("PERMISSION:%s:update", entity));
    requests
        .requestMatchers(HttpMethod.DELETE, path)
        .hasAuthority(String.format("PERMISSION:%s:delete", entity));
  }
}
