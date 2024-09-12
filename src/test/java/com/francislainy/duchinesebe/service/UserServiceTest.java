package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.entity.UserEntity;
import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.repository.UserRepository;
import com.francislainy.duchinesebe.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    //todo: add more tests (negative tests) and exception handling - 2024-09-12
    @Test
    void shouldCreateUser() {

        User user = User.builder()
                .id(randomUUID())
                .username("user")
                .password("password")
                .build();

        UserEntity userEntity = user.toEntity();

        when(userRepository.save(userEntity)).thenReturn(userEntity);

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser, "User should not be null");

        assertAll(
                () -> assertEquals(user.getId(), createdUser.getId(), "User ID should match"),
                () -> assertEquals(user.getUsername(), createdUser.getUsername(), "Username should match"),
                () -> assertEquals(user.getPassword(), createdUser.getPassword(), "Password should match")
        );
    }
}
