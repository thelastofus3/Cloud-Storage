package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.folder.*;
import com.thelastofus.cloudstorage.service.FolderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderController {

    private static final String FOLDER_UPLOAD = "/folder/upload";
    private static final String FOLDER_CREATE = "/folder/create";
    private static final String FOLDER_REMOVE = "/folder/remove";
    private static final String FOLDER_DOWNLOAD = "/folder/download";
    private static final String FOLDER_RENAME = "/folder/rename";

    FolderService folderService;

    @PostMapping(FOLDER_UPLOAD)
    public String uploadFolder(@Valid @ModelAttribute("folderUpload") FolderUploadRequest folderUploadRequest,
                               BindingResult bindingResult, Model model) {
        handleBindingResultErrors(bindingResult, model);

        folderService.upload(folderUploadRequest);
        log.info("Folder success save");

        return "redirect:/";
    }

    @PostMapping(FOLDER_CREATE)
    public String createFolder(@Valid @ModelAttribute("folderCreate") FolderCreateRequest folderCreateRequest,
                               BindingResult bindingResult, Model model) {
        handleBindingResultErrors(bindingResult, model);

        folderService.create(folderCreateRequest);
        log.info("Folder success create");

        return "redirect:/";
    }

    @DeleteMapping(FOLDER_REMOVE)
    public String removeFolder(@Valid @ModelAttribute("folderRemove") FolderRemoveRequest folderRemoveRequest,
                               BindingResult bindingResult, Model model) {
        handleBindingResultErrors(bindingResult, model);

        folderService.remove(folderRemoveRequest);
        log.debug("Folder success remove from minio");

        return "redirect:/";
    }

    @PatchMapping(FOLDER_RENAME)
    public String renameFolder(@Valid @ModelAttribute("folderRename")FolderRenameRequest folderRenameRequest,
                               BindingResult bindingResult, Model model) {
        handleBindingResultErrors(bindingResult, model);

        folderService.rename(folderRenameRequest);
        log.debug("Folder success rename");

        return "redirect:/";
    }

    @ResponseBody
    @GetMapping(FOLDER_DOWNLOAD)
    public ResponseEntity<ByteArrayResource> downloadFolder(@Valid @ModelAttribute("folderDownload") FolderDownloadRequest folderDownloadRequest,
                                                            BindingResult bindingResult, Model model) {
        handleBindingResultErrors(bindingResult, model);

        ByteArrayResource resource = folderService.download(folderDownloadRequest);

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + URLEncoder.encode(folderDownloadRequest.getName(), StandardCharsets.UTF_8).replace("+", "%20") + ".zip")
                .body(resource);
    }

    private void handleBindingResultErrors(BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            model.addAttribute("errorMessages", bindingResult.getAllErrors());
    }
}
