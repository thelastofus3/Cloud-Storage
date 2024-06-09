package com.thelastofus.cloudstorage.util.size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public enum SizeUnit {
    BYTES(" B"),
    KIBIBYTES(" KiB"),
    MEBIBYTES(" MiB");

    String abbreviation;
}
