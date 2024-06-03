package com.thelastofus.cloudstorage.dto.folder;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderRemoveRequest extends FolderRequest{

    String path;

    String owner;

}
