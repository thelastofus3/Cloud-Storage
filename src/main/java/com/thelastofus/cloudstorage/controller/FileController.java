package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import com.thelastofus.cloudstorage.service.FileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FileController {

    private static final String FILE_UPLOAD = "/file/upload";

    FileService fileService;

    @PostMapping(FILE_UPLOAD)
    public String uploadFile(@Valid @ModelAttribute("fileUpload")  FileUploadRequest fileUploadRequest,
//                             @RequestParam(value = "path",required = false,defaultValue = "") String currentPath,
                             Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redirect:/";

        fileService.upload(fileUploadRequest,principal);
        log.error("File success save in minio");

        return "redirect:/";
    }
}
