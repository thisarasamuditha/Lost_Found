package com.thisara.LNF.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for stateless APIs (like yours)
                .csrf(csrf -> csrf.disable())

                // Enable CORS for cross-origin requests
                .cors(Customizer.withDefaults())

                // Configure authorization rules for endpoints
                .authorizeHttpRequests(auth -> auth
                        // Public access to registration and login endpoints
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

                        // Allow public access to POST /api/items and category endpoints
                        .requestMatchers("/api/items").permitAll()
                        .requestMatchers("/api/items/**").permitAll()
                        .requestMatchers("/api/items/category/**").permitAll() // Allow public access to category endpoint

                        // Require authentication for all other requests
                        .anyRequest().authenticated()
                )

                // Disable default login page (you don’t need it here)
                .formLogin().disable()

                // Stateless session management for API (important for using tokens or basic auth)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Ensures no session is created

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Injecting AuthenticationManager (only needed if you are using authentication)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
