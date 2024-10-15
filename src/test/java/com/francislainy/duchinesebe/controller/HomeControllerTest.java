package com.francislainy.duchinesebe.controller;

import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    @WithMockUser(username = "validUser", password = "validPassword")
    void shouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginPage"));
    }

    @Test
    void shouldNotReturnLoginPageForInvalidUser() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "validUser", password = "validPassword")
    void shouldReturnAdminPage() throws Exception {
        User user1 = User.builder().id(UUID.randomUUID()).username("admin").build();
        User user2 = User.builder().id(UUID.randomUUID()).username("user").build();
        List<User> users = List.of(user1, user2);

        Mockito.when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPage"))
                .andExpect(model().attribute("users", users));

    }

}
