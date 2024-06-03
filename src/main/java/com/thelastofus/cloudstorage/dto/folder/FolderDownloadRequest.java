package com.thelastofus.cloudstorage.dto.folder;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderDownloadRequest {

    String folderName;

    String path;

    String owner;

}
