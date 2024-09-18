package com.francislainy.duchinesebe.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.francislainy.duchinesebe.model.Lesson;
import com.francislainy.duchinesebe.model.views.LessonViews;
import com.francislainy.duchinesebe.service.impl.LessonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonServiceImpl lessonService;

    @PostMapping
    public ResponseEntity<Object> createLesson(@RequestBody Lesson lesson) {
        return new ResponseEntity<>(lessonService.createLesson(lesson), HttpStatus.CREATED);
    }

    @GetMapping
    @JsonView(LessonViews.ListView.class) // Only list view fields will be serialized
    public ResponseEntity<Object> getLessons() {
        return new ResponseEntity<>(lessonService.getLessons(), HttpStatus.OK);
    }

    @GetMapping("/{lessonId}")
    @JsonView(LessonViews.DetailView.class) // Both list and detail view fields will be serialized
    public ResponseEntity<Object> getLesson(@PathVariable UUID lessonId) {
        return new ResponseEntity<>(lessonService.getLesson(lessonId), HttpStatus.OK);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable UUID lessonId) {
        lessonService.deleteLesson(lessonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
