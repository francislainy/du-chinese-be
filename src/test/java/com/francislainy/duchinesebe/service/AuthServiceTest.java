package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.entity.UserEntity;
import com.francislainy.duchinesebe.enums.UserType;
import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.repository.UserRepository;
import com.francislainy.duchinesebe.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//todo: add more tests (negative tests) and exception handling (on controller side) - 2024-09-12
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    AuthServiceImpl authService;

    @Mock
    UserRepository userRepository;

    @Test
    void shouldRegisterUser() {
        User user = User.builder()
                .id(randomUUID())
                .username("user")
                .password("password")
                .role(UserType.USER.toString())
                .build();

        UserEntity userEntity = user.toEntity();

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        User createdUser = authService.registerUser(user);
        assertNotNull(createdUser, "User should not be null");

        assertAll(
                () -> assertEquals(user.getId(), createdUser.getId(), "User ID should match"),
                () -> assertEquals(user.getUsername(), createdUser.getUsername(), "Username should match"),
                () -> assertEquals(user.getPassword(), createdUser.getPassword(), "Password should match"),
                () -> assertEquals(user.getRole(), createdUser.getRole(), "Role should match")
        );

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldLoginUser() {
        String username = "user";
        String password = "password";
        UserEntity userEntity = UserEntity.builder()
                .id(randomUUID())
                .username(username)
                .password(password)
                .role(UserType.USER.toString())
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        User loggedInUser = authService.loginUser(user);

        assertNotNull(loggedInUser, "Logged in user should not be null");

        assertAll(
                () -> assertEquals(userEntity.getId(), loggedInUser.getId(), "User ID should match"),
                () -> assertEquals(userEntity.getUsername(), loggedInUser.getUsername(), "Username should match"),
                () -> assertEquals(userEntity.getPassword(), loggedInUser.getPassword(), "Password should match"),
                () -> assertEquals(userEntity.getRole(), loggedInUser.getRole(), "Role should match")
        );

        verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldNotLoginUserWhenUserNotFound() {
        String username = "user";
        String password = "password";
        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> authService.loginUser(user));

        verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldNotLoginUserWhenInvalidPassword() {
        String username = "user";
        String password = "password";
        UserEntity userEntity = UserEntity.builder()
                .id(randomUUID())
                .username(username)
                .password("invalidPassword")
                .role(UserType.USER.toString())
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        assertThrows(IllegalArgumentException.class, () -> authService.loginUser(user));

        verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldLogoutUser() {
        //todo: implement this test - 2024-10-02
    }
}
