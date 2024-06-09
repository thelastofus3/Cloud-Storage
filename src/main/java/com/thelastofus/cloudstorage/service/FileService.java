package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.file.FileDownloadRequest;
import com.thelastofus.cloudstorage.dto.file.FileRemoveRequest;
import com.thelastofus.cloudstorage.dto.file.FileRenameRequest;
import com.thelastofus.cloudstorage.dto.file.FileUploadRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface FileService {
    void upload(FileUploadRequest fileUploadRequest);

    void remove(FileRemoveRequest fileRemoveRequest);

    ByteArrayResource download(FileDownloadRequest fileDownloadRequest);

    void rename(FileRenameRequest fileRenameRequest);

}
