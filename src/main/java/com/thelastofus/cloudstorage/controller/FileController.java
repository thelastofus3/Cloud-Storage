package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.dto.file.FileDownloadRequest;
import com.thelastofus.cloudstorage.dto.file.FileRemoveRequest;
import com.thelastofus.cloudstorage.dto.file.FileRenameRequest;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileController {

    private static final String FILE_UPLOAD = "/file/upload";
    private static final String FILE_REMOVE = "/file/remove";
    private static final String FILE_DOWNLOAD = "file/download";
    private static final String FILE_RENAME = "file/rename";

    FileService fileService;

    @PostMapping(FILE_UPLOAD)
    public String uploadFile(@Valid @ModelAttribute("fileUpload") FileUploadRequest fileUploadRequest,
                             BindingResult bindingResult, Model model) {
        handleBindingResultErrors(bindingResult, model);

        fileService.upload(fileUploadRequest);
        log.debug("File success save in minio");

        return "redirect:/";
    }

    @DeleteMapping(FILE_REMOVE)
    public String removeFile(@Valid @ModelAttribute("fileRemove") FileRemoveRequest fileRemoveRequest,
                             BindingResult bindingResult, Model model) {
        handleBindingResultErrors(bindingResult, model);

        fileService.remove(fileRemoveRequest);
        log.debug("File success remove from minio");

        return "redirect:/";
    }

    @PatchMapping(FILE_RENAME)
    public String renameFile(@Valid @ModelAttribute("fileRename") FileRenameRequest fileRenameRequest,
                             BindingResult bindingResult, Model model) {
        handleBindingResultErrors(bindingResult, model);

        fileService.rename(fileRenameRequest);
        log.debug("File success rename");

        return "redirect:/";
    }

    @ResponseBody
    @GetMapping(FILE_DOWNLOAD)
    public ResponseEntity<ByteArrayResource> downloadFile(@Valid @ModelAttribute("fileDownload") FileDownloadRequest fileDownloadRequest,
                                                          BindingResult bindingResult, Model model) {
        handleBindingResultErrors(bindingResult, model);

        ByteArrayResource resource = fileService.download(fileDownloadRequest);
        log.debug("File success download from minio");

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileDownloadRequest.getName(), StandardCharsets.UTF_8).replace("+", "%20"))
                .body(resource);

    }

    private void handleBindingResultErrors(BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            model.addAttribute("errorMessages", bindingResult.getAllErrors());
    }
}
