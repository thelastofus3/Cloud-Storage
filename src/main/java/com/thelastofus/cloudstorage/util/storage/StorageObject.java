package com.thelastofus.cloudstorage.util.storage;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StorageObject {

    String name;
    String path;
    String lastModified;
    String size;
    Boolean isDir;

}
