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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
                               Principal principal) {

        folderService.upload(folderUploadRequest, principal);
        log.info("Folder success save");

        return "redirect:/";
    }

    @PostMapping(FOLDER_CREATE)
    public String createFolder(@Valid @ModelAttribute("folderCreate") FolderCreateRequest folderCreateRequest,
                               Principal principal) {

        folderService.create(folderCreateRequest, principal);
        log.info("Folder success create");

        return "redirect:/";
    }

    @DeleteMapping(FOLDER_REMOVE)
    public String removeFolder(@ModelAttribute("folderRemove") FolderRemoveRequest folderRemoveRequest,
                               Principal principal) {
        folderService.remove(folderRemoveRequest, principal);
        log.debug("Folder success remove from minio");

        return "redirect:/";
    }

    @PatchMapping(FOLDER_RENAME)
    public String renameFolder(@Valid @ModelAttribute("folderRename")FolderRenameRequest folderRenameRequest,
                               Principal principal) {

        folderService.rename(folderRenameRequest, principal);
        log.debug("Folder success rename");

        return "redirect:/";
    }

    @ResponseBody
    @GetMapping(FOLDER_DOWNLOAD)
    public ResponseEntity<ByteArrayResource> downloadFolder(@ModelAttribute("folderDownload") FolderDownloadRequest folderDownloadRequest,
                                                            Principal principal) {
        ByteArrayResource resource = folderService.download(folderDownloadRequest, principal);

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + folderDownloadRequest.getName() + ".zip")
                .body(resource);
    }
}
