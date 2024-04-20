package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import org.springframework.stereotype.Service;

@Service
public interface FileService {
    void upload(FileUploadRequest fileUploadRequest);
}
