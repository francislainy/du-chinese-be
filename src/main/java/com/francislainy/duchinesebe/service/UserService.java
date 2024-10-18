package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void favouriteLesson(UUID lessonId);
    void unfavouriteLesson(UUID lessonId);

    boolean isLessonFavouritedByCurrentUser(UUID lessonId);
    boolean isLessonReadByCurrentUser(UUID lessonId);

    void readLesson(UUID lessonId);
    void unreadLesson(UUID lessonId);

    List<User> getUsers();
    User getUser(UUID userId);

    void resetCurrentUserProgress();

    void resetProgressForUser(UUID userId);
}
