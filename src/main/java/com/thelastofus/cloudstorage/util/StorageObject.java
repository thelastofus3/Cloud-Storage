package com.thelastofus.cloudstorage.util;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

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
    @Builder.Default
    String lastModified = " ";

    String size;


}
