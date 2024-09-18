package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.model.Lesson;
import com.francislainy.duchinesebe.repository.LessonRepository;
import com.francislainy.duchinesebe.service.impl.LessonServiceImpl;
import com.francislainy.duchinesebe.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.francislainy.duchinesebe.enums.LessonLevel.NEWBIE;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @InjectMocks
    LessonServiceImpl lessonService;

    @Mock
    UserServiceImpl userService;

    @Mock
    LessonRepository lessonRepository;

    @Test
    void shouldGetLessonsWithFavouritedByAndReadByCurrentUser() {
        UUID lessonId1 = randomUUID();
        UUID lessonId2 = randomUUID();

        LessonEntity lessonEntity1 = LessonEntity.builder()
                .id(lessonId1)
                .date(LocalDate.now())
                .type("grammar")
                .imageUrl("Lesson 1")
                .title("Lesson 1")
                .content("Lesson 1")
                .level(NEWBIE.toString())
                .favouritedByCurrentUser(true)
                .readByCurrentUser(false)
                .build();

        LessonEntity lessonEntity2 = LessonEntity.builder()
                .id(lessonId2)
                .date(LocalDate.now())
                .type("grammar")
                .imageUrl("Lesson 2")
                .title("Lesson 2")
                .content("Lesson 2")
                .level(NEWBIE.toString())
                .favouritedByCurrentUser(false)
                .readByCurrentUser(true)
                .build();

        List<LessonEntity> lessonEntityList = List.of(lessonEntity1, lessonEntity2);
        when(lessonRepository.findAll()).thenReturn(lessonEntityList);

        when(userService.isLessonFavouritedByCurrentUser(lessonEntity1.getId())).thenReturn(true);
        when(userService.isLessonFavouritedByCurrentUser(lessonEntity2.getId())).thenReturn(false);

        List<Lesson> lessonList = lessonService.getLessons();

        assertFalse(lessonList.isEmpty(), "Lesson list should not be empty");
        assertEquals(2, lessonList.size(), "Lesson list should have 2 lessons");

        Lesson lesson1 = lessonList.get(0);
        Lesson lesson2 = lessonList.get(1);
        assertAll(
                () -> assertEquals(lessonEntity1.getDate(), lesson1.getDate(), "Date for lesson 1 should match"),
                () -> assertEquals(lessonEntity1.getType(), lesson1.getType(), "Type for lesson 1 should match"),
                () -> assertEquals(lessonEntity1.getImageUrl(), lesson1.getImageUrl(), "Image URL for lesson 1 should match"),
                () -> assertEquals(lessonEntity1.getTitle(), lesson1.getTitle(), "Title for lesson 1 should match"),
                () -> assertEquals(lessonEntity1.getContent(), lesson1.getContent(), "Content for lesson 1 should match"),
                () -> assertEquals(lessonEntity1.getLevel(), lesson1.getLevel(), "Level for lesson 1 should match"),
                () -> assertEquals(true, lesson1.isFavouritedByCurrentUser(), "Is favourited for lesson 1 should match"),
                () -> assertEquals(false, lesson1.isReadByCurrentUser(), "Is read for lesson 1 should match"),

                () -> assertEquals(lessonEntity2.getDate(), lesson2.getDate(), "Date for lesson 2 should match"),
                () -> assertEquals(lessonEntity2.getType(), lesson2.getType(), "Type for lesson 2 should match"),
                () -> assertEquals(lessonEntity2.getImageUrl(), lesson2.getImageUrl(), "Image URL for lesson 2 should match"),
                () -> assertEquals(lessonEntity2.getTitle(), lesson2.getTitle(), "Title for lesson 2 should match"),
                () -> assertEquals(lessonEntity2.getContent(), lesson2.getContent(), "Content for lesson 2 should match"),
                () -> assertEquals(lessonEntity2.getLevel(), lesson2.getLevel(), "Level for lesson 2 should match"),
                () -> assertEquals(false, lesson2.isFavouritedByCurrentUser(), "Is favourited for lesson 2 should match"),
                () -> assertEquals(true, lesson2.isReadByCurrentUser(), "Is read for lesson 2 should match")

        );


        verify(lessonRepository, times(1)).findAll();
        verify(userService, times(1)).isLessonFavouritedByCurrentUser(lessonEntity1.getId());
        verify(userService, times(1)).isLessonFavouritedByCurrentUser(lessonEntity2.getId());
    }

    @Test
    void shouldCreateLesson() {
        Lesson lesson = Lesson.builder()
                .id(randomUUID())
                .date(LocalDate.now())
                .type("grammar")
                .imageUrl("Lesson 1")
                .title("Lesson 1")
                .content("Lesson 1")
                .level("NEWBIE")
                .build();

        LessonEntity lessonEntity = lesson.toEntity();

        when(lessonRepository.save(lessonEntity)).thenReturn(lessonEntity);

        Lesson createdLesson = lessonService.createLesson(lesson);
        assertAll(
                () -> assertEquals(lessonEntity.getId(), createdLesson.getId(), "ID should match"),
                () -> assertEquals(lessonEntity.getDate(), createdLesson.getDate(), "Date should match"),
                () -> assertEquals(lessonEntity.getType(), createdLesson.getType(), "Type should match"),
                () -> assertEquals(lessonEntity.getImageUrl(), createdLesson.getImageUrl(), "Image URL should match"),
                () -> assertEquals(lessonEntity.getTitle(), createdLesson.getTitle(), "Title should match"),
                () -> assertEquals(lessonEntity.getContent(), createdLesson.getContent(), "Content should match"),
                () -> assertEquals(lessonEntity.getLevel(), createdLesson.getLevel(), "Level should match")
        );

        verify(lessonRepository, times(1)).save(lessonEntity);
    }

    @Test
    void shouldDeleteLesson() {
        UUID lessonId = randomUUID();

        when(lessonRepository.existsById(lessonId)).thenReturn(true);

        assertDoesNotThrow(() -> lessonService.deleteLesson(lessonId));

        verify(lessonRepository, times(1)).existsById(lessonId);
        verify(lessonRepository, times(1)).deleteById(lessonId);
    }

    @Test
    void shouldThrowExceptionWhenLessonDoesNotExist() {
        UUID lessonId = randomUUID();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> lessonService.deleteLesson(lessonId));
        assertEquals("Lesson not found", exception.getMessage());

        verify(lessonRepository, never()).deleteById(lessonId);
    }
}
