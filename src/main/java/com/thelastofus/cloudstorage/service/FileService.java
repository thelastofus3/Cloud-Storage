package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.FileRemoveRequest;
import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface FileService {
    void upload(FileUploadRequest fileUploadRequest, Principal principal);

    void remove(FileRemoveRequest fileRemoveRequest, Principal principal);

}
