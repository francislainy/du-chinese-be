package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.model.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    List<Lesson> getLessons();

    Lesson createLesson(Lesson lesson);

    void deleteLesson(UUID lessonId);
}
