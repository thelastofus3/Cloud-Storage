package com.thelastofus.cloudstorage.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class MainController {

    private static final String HOME = "/";

    @ModelAttribute
    public void addAttributes(Principal principal, Model model){
        model.addAttribute("username", principal.getName());
    }

    @GetMapping(HOME)
    public String test(){
        return "test";
    }
}