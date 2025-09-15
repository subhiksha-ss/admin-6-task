package com.example.userManagement.security;

import com.example.userManagement.model.User;
import com.example.userManagement.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,  // http request from client entered in postman 
                                    @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain) throws ServletException, IOException {
                                        // filterChain -> it passes request to next filter (controller)

        String authHeader = request.getHeader("Authorization");  // bearer token

        if(authHeader != null && authHeader.startsWith("Bearer ")) {  // check token is present in header (beader token)
            String token = authHeader.substring(7);
            if(jwtUtil.validateToken(token)){ // check whether token is right or wrong and it does not expire 
                String email = jwtUtil.extractEmail(token);   // get email from token 
                User user = userRepository.findByEmail(email).orElse(null);  // find wether ther is any email in database

                if(user != null){ // if user is there 
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,   // no password required
                                    user.getRoles().stream()
                                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                                            .collect(Collectors.toList())
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);  // it will go to next filter send to controller
    }
}
