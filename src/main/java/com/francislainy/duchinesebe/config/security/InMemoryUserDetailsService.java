package com.francislainy.duchinesebe.config.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

//todo: unused - use it when retrieving the user from the database after it's no longer hardcoded - 2024/09/11
@AllArgsConstructor
public class InMemoryUserDetailsService implements UserDetailsService {

    private final List<UserDetails> users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
