package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.model.Lesson;

import java.util.List;

public interface LessonService {

    List<Lesson> getLessons();

    Lesson createLesson(Lesson lesson);
}
