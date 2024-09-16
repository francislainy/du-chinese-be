package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.model.User;

import java.util.UUID;

public interface UserService {

    User createUser(User user);

    void favouriteLesson(UUID lessonId);

    void unfavouriteLesson(UUID lessonId);
}
