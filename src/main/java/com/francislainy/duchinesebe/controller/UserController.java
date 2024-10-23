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

    @PostMapping("/reset-progress/{userId}")
    public ResponseEntity<Void> resetProgressForUser(@PathVariable UUID userId) {
        userService.resetProgressForUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


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

    @PostMapping("/make-admin/{userId}")
    public ResponseEntity<Void> updateUserToAdmin(@PathVariable UUID userId) {
        userService.updateUserToAdmin(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/revert-admin/{userId}")
    public ResponseEntity<Void> revertUserFromAdmin(@PathVariable UUID userId) {
        userService.revertUserFromAdmin(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
