package com.thelastofus.cloudstorage.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class FolderUploadRequest {

    @NotNull(message = "Name of the folder should not be null")
    MultipartFile folder;

}
