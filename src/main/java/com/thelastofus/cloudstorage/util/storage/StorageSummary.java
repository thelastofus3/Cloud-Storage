package com.thelastofus.cloudstorage.util.storage;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StorageSummary {

    Integer countOfObjects;

    String currentPath;

    LocalDateTime creationDate;


}
