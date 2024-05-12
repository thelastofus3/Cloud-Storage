package com.thelastofus.cloudstorage.dto;

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

    String path;
    @Builder.Default
    String lastModified = " ";

    String size;


}
