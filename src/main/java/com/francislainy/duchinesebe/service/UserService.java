package com.francislainy.duchinesebe.service;

import java.util.UUID;

public interface UserService {
    void favouriteLesson(UUID lessonId);
    void unfavouriteLesson(UUID lessonId);

    boolean isLessonFavouritedByCurrentUser(UUID lessonId);
    boolean isLessonReadByCurrentUser(UUID lessonId);

    void readLesson(UUID lessonId);
    void unreadLesson(UUID lessonId);
}
