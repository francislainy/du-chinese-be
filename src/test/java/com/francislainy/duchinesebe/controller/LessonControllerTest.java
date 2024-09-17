package com.francislainy.duchinesebe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.francislainy.duchinesebe.config.TestSecurityConfig;
import com.francislainy.duchinesebe.model.Lesson;
import com.francislainy.duchinesebe.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.francislainy.duchinesebe.enums.LessonLevel.NEWBIE;
import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonController.class)
@Import(TestSecurityConfig.class)
public class LessonControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @MockBean
    LessonServiceImpl lessonService;
    
    @Test
    void shouldGetListOfLessons() throws Exception {
        Lesson lessonResponse = Lesson.builder()
                .id(randomUUID())
                .date(now())
                .type("grammar")
                .imageUrl("Lesson 1")
                .title("Lesson 1")
                .content("Lesson 1")
                .level(NEWBIE.toString())
                .favouritedByCurrentUser(true)
                .build();

        List<Lesson> lessonList = List.of(lessonResponse);

        when(lessonService.getLessons()).thenReturn(lessonList);

        mockMvc.perform(get("/api/v1/lessons"))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(lessonList)));

        verify(lessonService, times(1)).getLessons();
    }

    @Test
    void shouldCreateLesson() throws Exception {
        Lesson lessonResponse = Lesson.builder()
                .id(randomUUID())
                .date(now())
                .type("grammar")
                .imageUrl("Lesson 1")
                .title("Lesson 1")
                .content("Lesson 1")
                .level(NEWBIE.toString())
                .favouritedByCurrentUser(true)
                .build();

        when(lessonService.createLesson(any(Lesson.class))).thenReturn(lessonResponse);

        mockMvc.perform(post("/api/v1/lessons")
                .contentType("application/json")
                .content(toJson(lessonResponse)))
                .andExpect(status().isCreated())
                .andExpect(content().string(toJson(lessonResponse)));

        verify(lessonService, times(1)).createLesson(any(Lesson.class));
    }

    public static String toJson(Object object) {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return om.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
