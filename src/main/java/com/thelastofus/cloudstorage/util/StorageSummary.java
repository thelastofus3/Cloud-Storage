package com.thelastofus.cloudstorage.util;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

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
