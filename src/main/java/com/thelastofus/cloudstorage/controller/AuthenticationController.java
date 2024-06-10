package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.user.UserRegistration;
import com.thelastofus.cloudstorage.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    private static final String AUTH_REGISTRATION = "/auth/registration";
    private static final String AUTH_LOGIN = "/auth/login";


    UserService userService;

    @GetMapping(AUTH_LOGIN)
    public String showLoginPage() {
        return "auth/login";
    }

    @GetMapping(AUTH_REGISTRATION)
    public String showRegistrationPage(@ModelAttribute("user") UserRegistration userRegistration) {
        return "auth/registration";
    }

    @PostMapping(AUTH_REGISTRATION)
    public String doRegistration(@ModelAttribute("user") @Valid UserRegistration userRegistration,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "auth/registration";

        userService.create(userRegistration);

        return "redirect:/auth/login";
    }
}
