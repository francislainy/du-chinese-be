package com.francislainy.duchinesebe.controller;

import com.francislainy.duchinesebe.config.TestSecurityConfig;
import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.francislainy.duchinesebe.controller.LessonControllerTest.toJson;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @Test
    void shouldCreateUser() throws Exception {
        User user = User.builder()
                .id(randomUUID())
                .username("user")
                .password("password")
                .build();

        when(userService.createUser(user)).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                .contentType(APPLICATION_JSON)
                .content(toJson(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(toJson(user)));

        verify(userService, times(1)).createUser(user);
    }
}
