package com.francislainy.duchinesebe.service.impl;

import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.model.Lesson;
import com.francislainy.duchinesebe.repository.LessonRepository;
import com.francislainy.duchinesebe.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final UserServiceImpl userService;

    @Override
    public List<Lesson> getLessons() {
        return lessonRepository.findAll().stream()
                .peek(lesson -> lesson.setFavouritedByCurrentUser(
                        userService.isLessonFavouritedByCurrentUser(lesson.getId())))
                .map(LessonEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Lesson createLesson(Lesson lesson) {
        LessonEntity lessonEntity = lesson.toEntity();
        return lessonRepository.save(lessonEntity).toModel();
    }

    @Override
    public void deleteLesson(UUID lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new RuntimeException("Lesson not found");
        }

        lessonRepository.deleteById(lessonId);
    }
}
