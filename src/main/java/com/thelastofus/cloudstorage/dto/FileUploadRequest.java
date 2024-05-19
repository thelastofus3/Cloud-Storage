package com.thelastofus.cloudstorage.dto;

import com.thelastofus.cloudstorage.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileUploadRequest {

    @NotNull(message = "Name of the file should not be empty")
    MultipartFile file;

    String path;

}
