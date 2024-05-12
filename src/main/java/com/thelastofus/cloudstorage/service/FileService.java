package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface FileService {
    void upload(FileUploadRequest fileUploadRequest, Principal principal, String currentPath);

}
