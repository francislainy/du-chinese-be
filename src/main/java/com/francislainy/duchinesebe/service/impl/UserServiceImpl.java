package com.francislainy.duchinesebe.service.impl;

import com.francislainy.duchinesebe.config.security.SecurityService;
import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.entity.UserEntity;
import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.repository.LessonRepository;
import com.francislainy.duchinesebe.repository.UserRepository;
import com.francislainy.duchinesebe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    private final SecurityService securityService;

    @Override
    public User createUser(User user) {
        return userRepository.save(user.toEntity()).toModel();
    }

    @Override
    public void favouriteLesson(UUID lessonId) {
        UserEntity userEntity = securityService.getCurrentUserEntity();

        LessonEntity lessonEntity = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        userEntity.getFavouritedLessons().add(lessonEntity);

        userRepository.save(userEntity);
    }

    @Override
    public void unfavouriteLesson(UUID lessonId) {
        UserEntity userEntity = securityService.getCurrentUserEntity();

        LessonEntity lessonEntity = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        userEntity.getFavouritedLessons().remove(lessonEntity);

        userRepository.save(userEntity);
    }

    @Override
    public boolean isLessonFavouritedByCurrentUser(UUID lessonId) {
        UserEntity userEntity = securityService.getCurrentUserEntity();

        return userEntity.getFavouritedLessons().stream()
                .anyMatch(lesson -> lesson.getId().equals(lessonId));
    }

    @Override
    public void readLesson(UUID lessonId) {
        UserEntity userEntity = securityService.getCurrentUserEntity();

        LessonEntity lessonEntity = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        userEntity.getReadLessons().add(lessonEntity);

        userRepository.save(userEntity);
    }

    @Override
    public void unreadLesson(UUID lessonId) {
        UserEntity userEntity = securityService.getCurrentUserEntity();

        LessonEntity lessonEntity = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        userEntity.getReadLessons().remove(lessonEntity);

        userRepository.save(userEntity);
    }
}
