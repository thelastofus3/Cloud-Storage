package com.thelastofus.cloudstorage.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExceptionBody {

    String message;
    Map<String,String> errors;

    public ExceptionBody(String message) {
        this.message = message;
    }
}
