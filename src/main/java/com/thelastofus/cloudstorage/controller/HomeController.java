package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import com.thelastofus.cloudstorage.dto.FolderUploadRequest;
import com.thelastofus.cloudstorage.dto.StorageObject;
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
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HomeController {

    private static final String HOME = "/";

    StorageService storageService;

    @ModelAttribute
    public void addAttributes(Principal principal, Model model){
        model.addAttribute("username", principal.getName());
    }

    @GetMapping(HOME)
    public String getHomePage(@ModelAttribute("fileUpload") FileUploadRequest fileUploadRequest,
                              @ModelAttribute("folderUpload")FolderUploadRequest folderUploadRequest,
                              Principal principal, Model model){

        log.debug("All users objects {}",storageService.getAllStorageObjects(principal));
        model.addAttribute("storageObjects",storageService.getAllStorageObjects(principal));

        return "file/upload";
    }
}
