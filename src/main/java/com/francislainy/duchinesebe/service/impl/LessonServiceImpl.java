package com.francislainy.duchinesebe.service.impl;

import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.model.Lesson;
import com.francislainy.duchinesebe.repository.LessonRepository;
import com.francislainy.duchinesebe.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final UserServiceImpl userService;

    @Override
    public List<Lesson> getLessons() {

        List<LessonEntity> lessons = lessonRepository.findAll();
        for (LessonEntity lesson : lessons) {
            boolean isFavourited = userService.isLessonFavouritedByCurrentUser(lesson.getId());
            lesson.setFavouritedByCurrentUser(isFavourited);
        }
        return lessons.stream().map(LessonEntity::toModel).collect(Collectors.toList());



//        return lessonRepository.findAll()
//                .stream()
//                .map(LessonEntity::toModel)
//                .toList();
    }

    @Override
    public Lesson createLesson(Lesson lesson) {
        LessonEntity lessonEntity = lesson.toEntity();
        return lessonRepository.save(lessonEntity).toModel();
    }
}
