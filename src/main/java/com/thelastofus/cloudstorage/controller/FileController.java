package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import com.thelastofus.cloudstorage.exception.FileUploadException;
import com.thelastofus.cloudstorage.model.Role;
import com.thelastofus.cloudstorage.model.User;
import com.thelastofus.cloudstorage.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FileController {

    FileService fileService;

    private static final String FILE_UPLOAD = "/file/upload";

    @ModelAttribute
    public void addAttributes(Principal principal, Model model){
        model.addAttribute("username", principal.getName());
    }

    @PostMapping(FILE_UPLOAD)
    public String uploadFile(@ModelAttribute("response") FileUploadRequest fileUploadRequest,
                             Principal principal,  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        fileService.upload(fileUploadRequest,principal);
        log.debug("File success save in minio");

        return "file/upload";
    }
}
