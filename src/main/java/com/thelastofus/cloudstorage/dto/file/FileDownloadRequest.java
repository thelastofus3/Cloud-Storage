package com.thelastofus.cloudstorage.dto.file;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileDownloadRequest extends FileRequest{

    String name;

    String path;

    String owner;

}
