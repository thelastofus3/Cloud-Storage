package com.thelastofus.cloudstorage.dto.folder;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderRemoveRequest extends FolderRequest {

    @NotNull(message = "Path of the folder should not be empty")
    String path;

    String owner;

}
