package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.folder.*;
import org.springframework.core.io.ByteArrayResource;

import java.security.Principal;

public interface FolderService {

    void upload(FolderUploadRequest folderUploadRequest);

    void create(FolderCreateRequest folderCreateRequest);

    void remove(FolderRemoveRequest folderRemoveRequest);

    ByteArrayResource download(FolderDownloadRequest folderDownloadRequest);

    void rename(FolderRenameRequest folderRenameRequest);

}
