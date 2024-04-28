package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import com.thelastofus.cloudstorage.service.StorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HomeController {

    StorageService storageService;
    private static final String HOME = "/";

    @ModelAttribute
    public void addAttributes(Principal principal, Model model){
        model.addAttribute("username", principal.getName());
    }

    @GetMapping(HOME)
    public String getHomePage(@ModelAttribute("response") FileUploadRequest fileUploadRequest,Model model){
        log.info("All users files {}",storageService.showAllFiles());
        model.addAttribute("files",storageService.showAllFiles());
        return "file/upload";
    }
}
