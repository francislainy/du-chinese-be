package com.francislainy.duchinesebe.controller;

import com.francislainy.duchinesebe.model.Lesson;
import com.francislainy.duchinesebe.model.views.LessonViews;
import com.francislainy.duchinesebe.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static com.francislainy.duchinesebe.enums.LessonLevel.NEWBIE;
import static com.francislainy.duchinesebe.utils.TestUtils.toJson;
import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LessonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LessonServiceImpl lessonService;

    @Test
    void shouldGetListOfLessons() throws Exception {
        // Mock a list of lessons without the content field
        Lesson lessonResponse = getLessonWithoutContent(); // Updated method to exclude content
        List<Lesson> lessonList = List.of(lessonResponse);

        when(lessonService.getLessons()).thenReturn(lessonList);

        mockMvc.perform(get("/api/v1/lessons"))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(lessonList, LessonViews.ListView.class))); // Use ListView for serialization

        verify(lessonService, times(1)).getLessons();
    }

    @Test
    void shouldCreateLesson() throws Exception {
        Lesson lessonResponse = getLessonWithContent();

        when(lessonService.createLesson(any(Lesson.class))).thenReturn(lessonResponse);

        mockMvc.perform(post("/api/v1/lessons")
                        .contentType("application/json")
                        .content(toJson(lessonResponse)))  // No need for @JsonView in this case
                .andExpect(status().isCreated())
                .andExpect(content().string(toJson(lessonResponse, LessonViews.DetailView.class)));

        verify(lessonService, times(1)).createLesson(any(Lesson.class));
    }

    @Test
    void shouldGetLessonById() throws Exception {
        UUID lessonId = randomUUID();
        Lesson lessonResponse = getLessonWithContent();  // Detailed view includes content

        when(lessonService.getLesson(any(UUID.class))).thenReturn(lessonResponse);

        mockMvc.perform(get("/api/v1/lessons/{lessonId}", lessonId))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(lessonResponse, LessonViews.DetailView.class))); // Use DetailView for serialization

        verify(lessonService, times(1)).getLesson(any(UUID.class));
    }

    @Test
    void shouldDeleteLesson() throws Exception {
        UUID lessonId = randomUUID();
        doNothing().when(lessonService).deleteLesson(lessonId);

        mockMvc.perform(delete("/api/v1/lessons/{lessonId}", lessonId))
                .andExpect(status().isNoContent());

        verify(lessonService, times(1)).deleteLesson(lessonId);
    }

    // Updated to exclude content
    private static Lesson getLessonWithoutContent() {
        return Lesson.builder()
                .id(randomUUID())
                .date(now())
                .type("grammar")
                .imageUrl("Lesson 1")
                .title("Lesson 1")
                .description("Lesson 1")
                .level(NEWBIE.toString())
                .favouritedByCurrentUser(true)
                .readByCurrentUser(true)
                .build();
    }

    // Detailed view includes content
    private static Lesson getLessonWithContent() {
        Lesson lesson = getLessonWithoutContent();
        lesson.setContent("Content of Lesson 1");

        return lesson;
    }
}
