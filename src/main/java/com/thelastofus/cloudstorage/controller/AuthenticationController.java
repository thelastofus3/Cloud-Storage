package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.UserDto;
import com.thelastofus.cloudstorage.mapper.UserMapper;
import com.thelastofus.cloudstorage.model.User;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {

    UserService userService;
    UserMapper userMapper;
    public static final String AUTH_REGISTRATION = "/auth/registration";
    public static final String AUTH_LOGIN = "/auth/login";

    @GetMapping(AUTH_LOGIN)
    public String getLoginPage(){
        return "auth/login";
    }
    @GetMapping(AUTH_REGISTRATION)
    public String getRegistrationPage(@ModelAttribute("user")UserDto userDto){
        return "auth/registration";
    }
    @PostMapping(AUTH_REGISTRATION)
    public String doRegistration(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "auth/registration";

        User user = userMapper.convertToUser(userDto);
        userService.create(user);

        return "redirect:/auth/login";
    }
}
