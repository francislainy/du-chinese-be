package com.francislainy.duchinesebe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
import java.util.UUID;

@Configuration
public class UserManagementConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails u1 = new SecurityUser(UUID.fromString("1a7b1d5e-8c1b-4b8e-9b1e-8c1b4b8e9b1e"), "john", "password1", "ADMIN");
        UserDetails u2 = new SecurityUser(UUID.fromString("2b7b1d5e-8c1b-4b8e-9b1e-8c1b4b8e9b1e"), "mary", "password2", "USER");
        List<UserDetails> users = List.of(u1, u2);
        return new InMemoryUserDetailsManager(users);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
