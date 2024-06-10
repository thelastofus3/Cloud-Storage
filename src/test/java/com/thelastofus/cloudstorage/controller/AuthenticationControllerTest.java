package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.user.UserRegistration;
import com.thelastofus.cloudstorage.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@WebMvcTest(controllers = AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void authenticationController_createUser_emptyEmail_success() throws Exception {
        UserRegistration userRegistration = new UserRegistration(
                "user1",
                "",
                "password",
                "password"
        );

        mockMvc.perform(post("/auth/registration")
                .content(userRegistration.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is2xxSuccessful());

        verify(userService, never()).create(any());
    }
    @Test
    void authenticationController_createUser_nullFields_success() throws Exception {
        UserRegistration userRegistration = new UserRegistration(
                null,
                null,
                null,
                null
        );

        mockMvc.perform(post("/auth/registration")
                        .content(userRegistration.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is2xxSuccessful());

        verify(userService, never()).create(any());
    }

    @Test
    void authenticationController_createUser_missMatchedPassword_success() throws Exception {
        UserRegistration userRegistration = new UserRegistration(
                "user1",
                "user1@gmail.com",
                "password1",
                "password2"
        );

        mockMvc.perform(post("/auth/registration")
                        .content(userRegistration.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is2xxSuccessful());

        verify(userService, never()).create(any());
    }
}