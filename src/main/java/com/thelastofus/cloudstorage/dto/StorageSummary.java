package com.thelastofus.cloudstorage.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
//
//    String creationDate;


}
