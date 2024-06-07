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
public class FolderDownloadRequest {

    @NotNull(message = "Name of the folder should not be empty")
    String name;

    String path;

    String owner;

}
