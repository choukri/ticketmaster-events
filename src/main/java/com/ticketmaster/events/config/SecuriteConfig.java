package com.ticketmaster.events.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static com.ticketmaster.events.util.Constant.*;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecuriteConfig {
    @Bean
    @Profile("!nosecurity")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz.requestMatchers(HttpMethod.GET, API_BASE_PATH + ENDPOINT_SEARCH_AVIALABLE_EVENTS).hasAuthority(SCOPE_OAUTH_READ_EVENTS).requestMatchers(HttpMethod.POST, API_BASE_PATH + ENDPOINT_SELECTION_BEST_SEATS).hasAuthority(SCOPE_OAUTH_READ_SEATS).anyRequest().denyAll()).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }

    @Bean
    @Profile("nosecurity")
    public SecurityFilterChain filterChainPermitAll(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests.anyRequest().permitAll());
        return httpSecurity.build();
    }
}
