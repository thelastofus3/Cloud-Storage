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
public class FileRenameRequest extends FileRequest {

    String from;
    @NotBlank(message = "Name of file should not be blank")
    String to;

    String owner;

}
