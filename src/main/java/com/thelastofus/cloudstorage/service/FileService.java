package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.file.FileDownloadRequest;
import com.thelastofus.cloudstorage.dto.file.FileRemoveRequest;
import com.thelastofus.cloudstorage.dto.file.FileUploadRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface FileService {
    void upload(FileUploadRequest fileUploadRequest, Principal principal);

    void remove(FileRemoveRequest fileRemoveRequest, Principal principal);

    ByteArrayResource download(FileDownloadRequest fileDownloadRequest, Principal principal);

}
