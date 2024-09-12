package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.model.Lesson;
import com.francislainy.duchinesebe.repository.LessonRepository;
import com.francislainy.duchinesebe.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static com.francislainy.duchinesebe.enums.LessonLevel.NEWBIE;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @InjectMocks
    LessonServiceImpl lessonService;

    @Mock
    LessonRepository lessonRepository;

    @Test
    void shouldGetLessons() {
        LessonEntity lessonEntity = LessonEntity.builder()
                .id(randomUUID())
                .date(LocalDate.now())
                .type("grammar")
                .imageUrl("Lesson 1")
                .title("Lesson 1")
                .content("Lesson 1")
                .level(NEWBIE.toString())
                .build();

        List<LessonEntity> lessonEntityList = List.of(lessonEntity);
        when(lessonRepository.findAll()).thenReturn(lessonEntityList);

        List<Lesson> lessonList = lessonService.getLessons();

        assertFalse(lessonList.isEmpty(), "Lesson list should not be empty");

        assertAll(
                () -> assertEquals(lessonEntity.getDate(), lessonList.get(0).getDate(), "Date should match"),
                () -> assertEquals(lessonEntity.getType(), lessonList.get(0).getType(), "Type should match"),
                () -> assertEquals(lessonEntity.getImageUrl(), lessonList.get(0).getImageUrl(), "Image URL should match"),
                () -> assertEquals(lessonEntity.getTitle(), lessonList.get(0).getTitle(), "Title should match"),
                () -> assertEquals(lessonEntity.getContent(), lessonList.get(0).getContent(), "Content should match"),
                () -> assertEquals(lessonEntity.getLevel(), lessonList.get(0).getLevel(), "Level should match")
        );

        verify(lessonRepository, times(1)).findAll();
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
}
