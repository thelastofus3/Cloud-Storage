package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.file.FileDownloadRequest;
import com.thelastofus.cloudstorage.dto.file.FileRemoveRequest;
import com.thelastofus.cloudstorage.dto.file.FileUploadRequest;
import com.thelastofus.cloudstorage.service.FileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FileController {

    private static final String FILE_UPLOAD = "/file/upload";
    private static final String FILE_REMOVE = "/file/remove";

    private static final String FILE_DOWNLOAD = "file/download";

    FileService fileService;

    @PostMapping(FILE_UPLOAD)
    public String uploadFile(@Valid @ModelAttribute("fileUpload")  FileUploadRequest fileUploadRequest,
                             Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redirect:/";

        fileService.upload(fileUploadRequest,principal);
        log.debug("File success save in minio");

        return "redirect:/";
    }

    @DeleteMapping(FILE_REMOVE)
    public String removeFile(@ModelAttribute("fileRemove")FileRemoveRequest fileRemoveRequest,
                             Principal principal) {
        fileService.remove(fileRemoveRequest, principal);
        log.debug("File success remove from minio");

        return "redirect:/";
    }

    @ResponseBody
    @GetMapping(FILE_DOWNLOAD)
    public ResponseEntity<ByteArrayResource> downloadFile(@ModelAttribute("fileDownload")FileDownloadRequest fileDownloadRequest,
                                                          Principal principal) {
        ByteArrayResource resource = fileService.download(fileDownloadRequest, principal);
        log.debug("File success download from minio");

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + fileDownloadRequest.getName())
                .body(resource);
        //TODO: Заменить "Content-Disposition" на CONTENT-DISPOSITION
    }

}
