package com.francislainy.duchinesebe.controller;

import com.francislainy.duchinesebe.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @Test
    void shouldFavouriteALesson() throws Exception {
        UUID lessonId = UUID.randomUUID();
        doNothing().when(userService).favouriteLesson(lessonId);

//        todo: check whether path should include the user id - 12/10/2021
//        mockMvc.perform(post("/api/v1/users/{userId}/favourite/{lessonId}", user.getId(), randomUUID()))
        mockMvc.perform(post("/api/v1/users/favourite/{lessonId}", lessonId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).favouriteLesson(lessonId);
    }

    @Test
    void shouldUnfavouriteALesson() throws Exception {
        UUID lessonId = UUID.randomUUID();
        doNothing().when(userService).unfavouriteLesson(lessonId);

        mockMvc.perform(post("/api/v1/users/unfavourite/{lessonId}", lessonId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).unfavouriteLesson(lessonId);
    }

    @Test
    void shouldMarkLessonAsRead() throws Exception {
        UUID lessonId = UUID.randomUUID();
        doNothing().when(userService).readLesson(lessonId);

        mockMvc.perform(post("/api/v1/users/read/{lessonId}", lessonId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).readLesson(lessonId);
    }

    @Test
    void shouldMarkLessonAsUnread() throws Exception {
        UUID lessonId = UUID.randomUUID();
        doNothing().when(userService).unreadLesson(lessonId);

        mockMvc.perform(post("/api/v1/users/unread/{lessonId}", lessonId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).unreadLesson(lessonId);
    }
}
