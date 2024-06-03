package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.folder.FolderCreateRequest;
import com.thelastofus.cloudstorage.dto.folder.FolderDownloadRequest;
import com.thelastofus.cloudstorage.dto.folder.FolderRemoveRequest;
import com.thelastofus.cloudstorage.dto.folder.FolderUploadRequest;
import org.springframework.core.io.ByteArrayResource;

import java.security.Principal;

public interface FolderService {

    void upload(FolderUploadRequest folderUploadRequest, Principal principal);

    void create(FolderCreateRequest folderCreateRequest, Principal principal);

    void remove(FolderRemoveRequest folderRemoveRequest, Principal principal);

    ByteArrayResource download(FolderDownloadRequest folderDownloadRequest, Principal principal);
}
