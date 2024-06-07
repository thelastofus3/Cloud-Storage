package com.thelastofus.cloudstorage.dto.file;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileRemoveRequest extends FileRequest {

    @NotNull(message = "Path of the file should not be empty")
    String path;

    String owner;

}
