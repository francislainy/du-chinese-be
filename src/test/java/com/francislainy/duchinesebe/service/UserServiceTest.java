package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.entity.UserEntity;
import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.repository.LessonRepository;
import com.francislainy.duchinesebe.repository.UserRepository;
import com.francislainy.duchinesebe.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    LessonRepository lessonRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;


    //todo: add more tests (negative tests) and exception handling (on controller side) - 2024-09-12
    @Test
    void shouldCreateUser() {
        User user = User.builder()
                .id(randomUUID())
                .username("user")
                .password("password")
                .build();

        UserEntity userEntity = user.toEntity();

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser, "User should not be null");

        assertAll(
                () -> assertEquals(user.getId(), createdUser.getId(), "User ID should match"),
                () -> assertEquals(user.getUsername(), createdUser.getUsername(), "Username should match"),
                () -> assertEquals(user.getPassword(), createdUser.getPassword(), "Password should match")
        );

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldFavouriteLesson() {
        authenticateUser();

        UUID userId = randomUUID();
        UUID lessonId = randomUUID();
        Set<LessonEntity> lessons = new HashSet<>();
        lessons.add(LessonEntity.builder().id(lessonId).build());

        LessonEntity lessonEntity = LessonEntity.builder().id(lessonId).build();
        UserEntity userEntity = UserEntity.builder().id(userId).username("anyUsername")
                .favouritedLessons(lessons)
                .build();

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(userEntity));
        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.of(lessonEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        assertDoesNotThrow(() -> userService.favouriteLesson(lessonId));

        verify(userRepository).findByUsername(any(String.class));
        verify(lessonRepository).findById(any(UUID.class));
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldNotFavouriteLessonWhenLessonNotFound() {
        authenticateUser();

        UUID userId = randomUUID();
        UUID lessonId = randomUUID();
        Set<LessonEntity> lessons = new HashSet<>();
        lessons.add(LessonEntity.builder().id(lessonId).build());

        UserEntity userEntity = UserEntity.builder().id(userId).username("anyUsername")
                .favouritedLessons(lessons)
                .build();

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(userEntity));
        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.favouriteLesson(lessonId));

        verify(userRepository).findByUsername(any(String.class));
        verify(lessonRepository).findById(any(UUID.class));
    }

    @Test
    void shouldNotFavouriteLessonWhenUserNotFound() {
        authenticateUser();

        UUID lessonId = randomUUID();

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.favouriteLesson(lessonId));

        verify(userRepository).findByUsername(any(String.class));
    }

    @Test
    void shouldUnfavouriteLesson() {
        authenticateUser();

        UUID userId = randomUUID();
        UUID lessonId = randomUUID();
        Set<LessonEntity> lessons = new HashSet<>();
        lessons.add(LessonEntity.builder().id(lessonId).build());

        LessonEntity lessonEntity = LessonEntity.builder().id(lessonId).build();
        UserEntity userEntity = UserEntity.builder().id(userId).username("anyUsername")
                .favouritedLessons(lessons)
                .build();

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(userEntity));
        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.of(lessonEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        assertDoesNotThrow(() -> userService.unfavouriteLesson(lessonId));

        verify(userRepository).findByUsername(any(String.class));
        verify(lessonRepository).findById(any(UUID.class));
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldNotUnfavouriteLessonWhenLessonNotFound() {
        authenticateUser();

        UUID userId = randomUUID();
        UUID lessonId = randomUUID();
        Set<LessonEntity> lessons = new HashSet<>();
        lessons.add(LessonEntity.builder().id(lessonId).build());

        UserEntity userEntity = UserEntity.builder().id(userId).username("anyUsername")
                .favouritedLessons(lessons)
                .build();

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(userEntity));
        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.unfavouriteLesson(lessonId));

        verify(userRepository).findByUsername(any(String.class));
        verify(lessonRepository).findById(any(UUID.class));
    }

    @Test
    void shouldNotUnfavouriteLessonWhenUserNotFound() {
        authenticateUser();

        UUID lessonId = randomUUID();

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.unfavouriteLesson(lessonId));

        verify(userRepository).findByUsername(any(String.class));
    }

    private void authenticateUser() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("testUser");
    }

    //todo: what to do when lesson not favourited - 2024-09-17
}
