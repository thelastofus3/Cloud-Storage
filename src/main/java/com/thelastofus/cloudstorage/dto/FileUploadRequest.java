package com.thelastofus.cloudstorage.dto;

import com.thelastofus.cloudstorage.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileUploadRequest {
    @NotBlank(message = "Name of the file should not be empty")
    MultipartFile file;
    @NotBlank(message = "Can not find the owner of the file")
    User owner;
}
