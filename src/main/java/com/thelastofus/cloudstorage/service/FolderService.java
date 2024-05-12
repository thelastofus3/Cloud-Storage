package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.FolderUploadRequest;

import java.security.Principal;

public interface FolderService {

    void upload(FolderUploadRequest folderUploadRequest, Principal principal, String currentPath);
}
