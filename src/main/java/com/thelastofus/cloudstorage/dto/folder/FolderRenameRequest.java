package com.thelastofus.cloudstorage.dto.folder;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Name of folder should not be blank")
    String to;

    String name;

    String owner;
}
