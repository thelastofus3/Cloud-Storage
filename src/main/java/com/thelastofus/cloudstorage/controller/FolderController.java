package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.folder.FolderCreateRequest;
import com.thelastofus.cloudstorage.dto.folder.FolderRemoveRequest;
import com.thelastofus.cloudstorage.dto.folder.FolderUploadRequest;
import com.thelastofus.cloudstorage.service.FolderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderController {

    private static final String FOLDER_UPLOAD = "/folder/upload";

    private static final String FOLDER_CREATE = "/folder/create";

    private static final String FOLDER_REMOVE = "/folder/remove";

    FolderService folderService;

    @PostMapping(FOLDER_UPLOAD)
    public String uploadFolder(@Valid @ModelAttribute("folderUpload")FolderUploadRequest folderUploadRequest,
                               Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redirect:/";

        folderService.upload(folderUploadRequest, principal);
        log.info("Folder success save");

        return "redirect:/";
    }

    @PostMapping(FOLDER_CREATE)
    public String createFolder(@Valid @ModelAttribute("folderCreate")FolderCreateRequest folderCreateRequest,
                               Principal principal,BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redirect:/";

        folderService.create(folderCreateRequest, principal);
        log.info("Folder success create");

        return "redirect:/";
    }

    @DeleteMapping(FOLDER_REMOVE)
    public String removeFile(@ModelAttribute("folderRemove")FolderRemoveRequest folderRemoveRequest,
                             Principal principal) {
        folderService.remove(folderRemoveRequest, principal);
        log.debug("Folder success remove from minio");

        return "redirect:/";
    }
}
