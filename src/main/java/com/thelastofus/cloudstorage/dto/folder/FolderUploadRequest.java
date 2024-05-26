package com.thelastofus.cloudstorage.dto.folder;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderUploadRequest extends FolderRequest{

    @NotNull(message = "Name of the folder should not be empty")
    List<MultipartFile> folder;

    String path;

}
