package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import com.thelastofus.cloudstorage.exception.FileUploadException;
import com.thelastofus.cloudstorage.model.Role;
import com.thelastofus.cloudstorage.model.User;
import com.thelastofus.cloudstorage.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

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

    @GetMapping(FILE_UPLOAD)
    public String getFileUploadPage(@ModelAttribute("response") FileUploadRequest fileUploadRequest) {
        fileUploadRequest.setOwner(new User(
                15L,"Gena","gena@gmail.com","password", Role.ROLE_USER)
        );
        return "file/upload";
    }

    @PostMapping(FILE_UPLOAD)
    public String uploadFile(Model model, @ModelAttribute("response") FileUploadRequest fileUploadRequest) {
        try {
            fileService.upload(fileUploadRequest);
        } catch (Exception e) {
            model.addAttribute("message","Minio error");
            throw new FileUploadException("File upload error");
        }
        model.addAttribute("message","File Successfully Upload");
        return "file/upload";
    }
}
