package com.thelastofus.cloudstorage.dto.file;

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
public class FileUploadRequest extends FileRequest{

    @NotNull(message = "Name of the file should not be empty")
    MultipartFile file;

    String path;

}
