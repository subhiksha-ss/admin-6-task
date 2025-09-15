package com.example.userManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration    //security setting
@EnableMethodSecurity   // method level security - @preAuthorize => only that role person can access the things
public class SecurityConfig {

    @Autowired  // dependencies injection
    private JwtAuthenticationFilter jwtAuthenticationFilter;    // calling jwt authentication filter for validation

    @Bean   // spring managed bean  => it will give  a application security
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
        // security filter chain => used to secure all the http rtequest that passes through it 
        http
            .csrf(csrf -> csrf.disable())  // jwt is sessionless so that we need to disable it (cross site request forgery)
            .authorizeHttpRequests(auth -> auth // used to define who can access the http method 
                .requestMatchers("/auth/login", "/auth/register").permitAll() // login and register can be accessed by everyone
                .requestMatchers("/admin/**").hasRole("ADMIN") // only admin can access all the things under admin endpoint
                .anyRequest().authenticated() // things that are not defined overhere can access by everyone 
            )
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT stateless
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
