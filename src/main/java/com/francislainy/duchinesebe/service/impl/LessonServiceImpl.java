package com.francislainy.duchinesebe.service.impl;

import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.model.Lesson;
import com.francislainy.duchinesebe.repository.LessonRepository;
import com.francislainy.duchinesebe.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public List<Lesson> getLessons() {
        return lessonRepository.findAll()
                .stream()
                .map(LessonEntity::toModel)
                .toList();
    }

    @Override
    public Lesson createLesson(Lesson lesson) {
        LessonEntity lessonEntity = lesson.toEntity();
        return lessonRepository.save(lessonEntity).toModel();
    }
}
