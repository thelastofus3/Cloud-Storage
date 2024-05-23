package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.FileRemoveRequest;
import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import com.thelastofus.cloudstorage.dto.FolderCreateRequest;
import com.thelastofus.cloudstorage.dto.FolderUploadRequest;
import com.thelastofus.cloudstorage.exception.FileRemoveException;
import com.thelastofus.cloudstorage.service.StorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

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
                              @ModelAttribute("folderCreate") FolderCreateRequest folderCreateRequest,
                              @ModelAttribute("fileRemove") FileRemoveRequest fileRemoveRequest,
                              @RequestParam(value = "path",required = false,defaultValue = "") String currentPath,
                              Principal principal, Model model){

        model.addAttribute("storageObjects",storageService.getAllStorageObjects(principal, currentPath));
        model.addAttribute("storageSummary",storageService.getStorageSummary(principal, currentPath));

        return "storage/upload";
    }
}
