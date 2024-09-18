package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.model.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    Lesson createLesson(Lesson lesson);

    List<Lesson> getLessons();

    Lesson getLesson(UUID lessonId);

    void deleteLesson(UUID lessonId);
}
