package com.francislainy.duchinesebe.service.impl;

import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.entity.UserEntity;
import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.repository.LessonRepository;
import com.francislainy.duchinesebe.repository.UserRepository;
import com.francislainy.duchinesebe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user.toEntity()).toModel();
    }

    @Override
    public void favouriteLesson(UUID lessonId) {
        String username = getCurrentUsername();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        LessonEntity lessonEntity = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        userEntity.getFavouritedLessons().add(lessonEntity);

        userRepository.save(userEntity);


    }

    @Override
    public void unfavouriteLesson(UUID lessonId) {
        String username = getCurrentUsername();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        LessonEntity lessonEntity = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        userEntity.getFavouritedLessons().remove(lessonEntity);

        userRepository.save(userEntity);
    }

    //todo: add tests for this method - 2024-09-17
    @Override
    public boolean isLessonFavouritedByCurrentUser(UUID lessonId) {
        String username = getCurrentUsername();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return userEntity.getFavouritedLessons().stream()
                .anyMatch(lesson -> lesson.getId().equals(lessonId));
    }

    private String getCurrentUsername() { //todo: move to own service class - 2024-09-17
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
