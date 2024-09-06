package com.francislainy.duchinesebe.controller;

import com.francislainy.duchinesebe.model.Lesson;
import com.francislainy.duchinesebe.service.impl.LessonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
@CrossOrigin("*") //todo: add test https://spring.io/guides/gs/rest-service-cors - 26/08/2024
public class LessonController {

    private final LessonServiceImpl lessonService;

    @GetMapping
    public ResponseEntity<Object> getLessons() {
        return new ResponseEntity<>(lessonService.getLessons(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createLesson(@RequestBody Lesson lesson) {
        return new ResponseEntity<>(lessonService.createLesson(lesson), HttpStatus.CREATED);
    }
}
