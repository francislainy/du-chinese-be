package com.francislainy.duchinesebe.controller;

import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.francislainy.duchinesebe.utils.TestUtils.toJson;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @Test
    void shouldRegisterUser() throws Exception {
        User user = User.builder()
                .username("test")
                .password("test")
//                .firstName("test")
//                .lastName("test")
                .build();

        when(userService.registerUser(user)).thenReturn(user);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isCreated());

        verify(userService).registerUser(user);
    }

    @Test
    void shouldLoginUser() throws Exception {
        User user = User.builder()
                .username("test")
                .password("test")
                .build();

        when(userService.loginUser(user)).thenReturn(user);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user)))
                .andExpect(status().isOk());

        verify(userService).loginUser(user);
    }
}
