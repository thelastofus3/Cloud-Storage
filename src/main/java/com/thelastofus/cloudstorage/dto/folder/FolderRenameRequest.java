package com.thelastofus.cloudstorage.dto.folder;

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
public class FolderRenameRequest extends FolderRequest{

    String from;
    @NotBlank(message = "Name of the folder should not be empty")
    String to;

    String name;

    String owner;
}
