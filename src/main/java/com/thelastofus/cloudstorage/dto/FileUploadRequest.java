package com.thelastofus.cloudstorage.dto;

import com.thelastofus.cloudstorage.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileUploadRequest {

    MultipartFile file;

    User owner;
}
