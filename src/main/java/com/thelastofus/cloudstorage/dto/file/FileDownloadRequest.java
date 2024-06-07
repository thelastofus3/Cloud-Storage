package com.thelastofus.cloudstorage.dto.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileDownloadRequest extends FileRequest {
    @NotNull(message = "Name of the file should not be empty")
    String name;

    String path;

    String owner;

}
