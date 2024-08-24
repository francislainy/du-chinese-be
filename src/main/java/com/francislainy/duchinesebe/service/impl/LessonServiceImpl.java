package com.francislainy.duchinesebe.service.impl;

import com.francislainy.duchinesebe.model.Lesson;
import com.francislainy.duchinesebe.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    @Override
    public List<Lesson> getLessons() {
        return List.of();
    }
}
