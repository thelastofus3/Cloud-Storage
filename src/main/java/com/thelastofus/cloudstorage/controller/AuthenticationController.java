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
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {

    UserService userService;
    UserMapper userMapper;

    @GetMapping("/login")
    public String getLoginPage(){
        return "auth/login";
    }
    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user")UserDto userDto){
        return "auth/registration";
    }
    @PostMapping("/registration")
    public String doRegistration(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "auth/registration";

        User user = userMapper.convertToUser(userDto);
        userService.create(user);

        return "redirect:/auth/login";
    }
}
