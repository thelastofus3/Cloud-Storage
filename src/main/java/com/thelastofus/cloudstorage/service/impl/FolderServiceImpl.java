package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.FolderUploadRequest;
import com.thelastofus.cloudstorage.exception.FolderUploadException;
import com.thelastofus.cloudstorage.repository.FolderRepository;
import com.thelastofus.cloudstorage.service.FolderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.Principal;

import static com.thelastofus.cloudstorage.util.StorageUtil.getUserFolder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderServiceImpl implements FolderService {

    FolderRepository folderRepository;

    @Override
    public void upload(FolderUploadRequest folderUploadRequest, Principal principal, String currentPath) {
        MultipartFile folder = folderUploadRequest.getFolder();
        if (folder.isEmpty() || folder.getOriginalFilename() == null)
            throw new FolderUploadException("Folder must have name and content");

        try {
            String folderName = getUserFolder(principal, currentPath) + folder.getOriginalFilename();
            InputStream inputStream = folder.getInputStream();
            folderRepository.saveFolder(inputStream, folderName);
        } catch (Exception e) {
            throw new FolderUploadException("Folder upload failed " + e.getMessage());
        }
    }
}
