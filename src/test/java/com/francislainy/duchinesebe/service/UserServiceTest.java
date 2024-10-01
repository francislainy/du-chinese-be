package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.config.security.SecurityService;
import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.entity.UserEntity;
import com.francislainy.duchinesebe.enums.UserType;
import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.repository.LessonRepository;
import com.francislainy.duchinesebe.repository.UserRepository;
import com.francislainy.duchinesebe.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//todo: add more tests (negative tests) and exception handling (on controller side) - 2024-09-12
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    LessonRepository lessonRepository;

    @Mock
    SecurityService securityService;

    UserEntity userEntity;
    LessonEntity lessonEntity;

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

        User createdUser = userService.registerUser(user);
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

        User loggedInUser = userService.loginUser(user);

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

        assertThrows(IllegalArgumentException.class, () -> userService.loginUser(user));

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

        assertThrows(IllegalArgumentException.class, () -> userService.loginUser(user));

        verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldFavouriteLesson() {
        authenticateUser();

        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.of(lessonEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        assertDoesNotThrow(() -> userService.favouriteLesson(lessonEntity.getId()));

        assertAll(
                () -> assertEquals(1, userEntity.getFavouritedLessons().size(), "Favourited lessons should have 1 lesson"),
                () -> assertEquals(lessonEntity, userEntity.getFavouritedLessons().iterator().next(), "Favourited lesson should match")
        );

        verify(lessonRepository).findById(any(UUID.class));
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldNotFavouriteLessonWhenLessonNotFound() {
        authenticateUser();

        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.favouriteLesson(randomUUID()));

        verify(lessonRepository).findById(any(UUID.class));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void shouldNotFavouriteLessonWhenUserNotFound() {
        UUID lessonId = randomUUID();
        assertThrows(IllegalArgumentException.class, () -> userService.favouriteLesson(lessonId));

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void shouldUnfavouriteLesson() {
        authenticateUser();

        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.of(lessonEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        assertDoesNotThrow(() -> userService.unfavouriteLesson(lessonEntity.getId()));

        assertAll(
                () -> assertEquals(0, userEntity.getFavouritedLessons().size(), "Favourited lessons should have 0 lesson")
        );

        verify(lessonRepository).findById(any(UUID.class));
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldNotUnfavouriteLessonWhenLessonNotFound() {
        authenticateUser();

        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.unfavouriteLesson(randomUUID()));

        verify(lessonRepository).findById(any(UUID.class));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    //todo: what to do when lesson not favourited before it's favourited - 2024-09-17
    @Test
    void shouldNotUnfavouriteLessonWhenUserNotFound() {
        UUID lessonId = randomUUID();
        assertThrows(IllegalArgumentException.class, () -> userService.unfavouriteLesson(lessonId));

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void shouldGetLessonFavouritedByCurrentUser() {
        authenticateUser();

        boolean isLessonFavouritedByCurrentUser = userService.isLessonFavouritedByCurrentUser(lessonEntity.getId());

        assertEquals(true, isLessonFavouritedByCurrentUser, "Lesson should be favourited by current user");
    }

    @Test
    void shouldGetLessonReadByCurrentUser() {
        authenticateUser();

        boolean isLessonReadByCurrentUser = userService.isLessonReadByCurrentUser(lessonEntity.getId());

        assertEquals(true, isLessonReadByCurrentUser, "Lesson should be read by current user");
    }

    @Test
    void shouldNotGetLessonNotFavouritedByCurrentUser() {
        authenticateUser();

        boolean isLessonFavouritedByCurrentUser = userService.isLessonFavouritedByCurrentUser(randomUUID());

        assertEquals(false, isLessonFavouritedByCurrentUser, "Lesson should not be favourited by current user");
    }

    @Test
    void shouldReadLesson() {
        authenticateUser();

        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.of(lessonEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        assertDoesNotThrow(() -> userService.readLesson(lessonEntity.getId()));

        assertAll(
                () -> assertEquals(1, userEntity.getReadLessons().size(), "Read lessons should have 1 lesson"),
                () -> assertEquals(lessonEntity, userEntity.getReadLessons().iterator().next(), "Read lesson should match")
        );

        verify(lessonRepository).findById(any(UUID.class));
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldNotReadLessonWhenLessonNotFound() {
        authenticateUser();

        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.readLesson(randomUUID()));

        verify(lessonRepository).findById(any(UUID.class));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void shouldNotReadLessonWhenUserNotFound() {
        UUID lessonId = randomUUID();
        assertThrows(IllegalArgumentException.class, () -> userService.readLesson(lessonId));

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void shouldUnreadLesson() {
        authenticateUser();

        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.of(lessonEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        assertDoesNotThrow(() -> userService.unreadLesson(lessonEntity.getId()));

        assertAll(
                () -> assertEquals(0, userEntity.getReadLessons().size(), "Read lessons should have 0 lesson")
        );

        verify(lessonRepository).findById(any(UUID.class));
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldNotUnreadLessonWhenLessonNotFound() {
        authenticateUser();

        when(lessonRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.unreadLesson(randomUUID()));

        verify(lessonRepository).findById(any(UUID.class));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void shouldNotUnreadLessonWhenUserNotFound() {
        UUID lessonId = randomUUID();
        assertThrows(IllegalArgumentException.class, () -> userService.unreadLesson(lessonId));

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    private void authenticateUser() {
        UUID userId = randomUUID();
        UUID lessonId = randomUUID();
        lessonEntity = LessonEntity.builder().id(lessonId).build();
        Set<LessonEntity> lessons = new HashSet<>();
        lessons.add(lessonEntity);

        userEntity = UserEntity.builder().id(userId).username("anyUsername")
                .favouritedLessons(lessons)
                .readLessons(lessons)
                .build();
        when(securityService.getCurrentUserEntity()).thenReturn(userEntity);
    }
}
