package com.francislainy.duchinesebe.controller;

import com.francislainy.duchinesebe.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    //todo: move to lesson controller - 2024/09/25
    @PostMapping("/favourite/{lessonId}")
    public ResponseEntity<Void> favouriteLesson(@PathVariable UUID lessonId) {
        userService.favouriteLesson(lessonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/unfavourite/{lessonId}")
    public ResponseEntity<Void> unfavouriteLesson(@PathVariable UUID lessonId) {
        userService.unfavouriteLesson(lessonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/read/{lessonId}")
    public ResponseEntity<Void> readLesson(@PathVariable UUID lessonId) {
        userService.readLesson(lessonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/unread/{lessonId}")
    public ResponseEntity<Void> unreadLesson(@PathVariable UUID lessonId) {
        userService.unreadLesson(lessonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
