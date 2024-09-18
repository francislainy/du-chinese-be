package com.francislainy.duchinesebe.controller;

import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.francislainy.duchinesebe.controller.LessonControllerTest.toJson;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LessonControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LessonRepository lessonRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminShouldCreateLesson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(getLessonEntity())))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userShouldNotCreateLesson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(getLessonEntity())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminShouldDeleteLesson() throws Exception {
        LessonEntity lessonEntity = getLessonEntity();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/lessons/{lessonId}", lessonEntity.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userShouldNotDeleteLesson() throws Exception {
        LessonEntity lessonEntity = getLessonEntity();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/lessons/{lessonId}", lessonEntity.getId()))
                .andExpect(status().isForbidden());
    }

    private LessonEntity getLessonEntity() {
       LessonEntity lessonEntity = LessonEntity.builder()
                .date(LocalDate.now())
                .type("grammar")
                .imageUrl("Lesson 1")
                .title("Lesson 1")
                .description("Lesson 1")
                .level("NEWBIE")
                .build();

        return lessonRepository.save(lessonEntity);
    }
}