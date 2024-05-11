package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.FolderUploadRequest;
import com.thelastofus.cloudstorage.exception.FileUploadException;
import com.thelastofus.cloudstorage.exception.FolderUploadException;
import com.thelastofus.cloudstorage.repository.FileRepository;
import com.thelastofus.cloudstorage.repository.FolderRepository;
import com.thelastofus.cloudstorage.service.FolderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.Principal;

import static com.thelastofus.cloudstorage.util.MinioUtil.getUserParentFolder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderServiceImpl implements FolderService {

    FolderRepository folderRepository;

    @Override
    public void upload(FolderUploadRequest folderUploadRequest, Principal principal) {
        MultipartFile folder = folderUploadRequest.getFolder();
        if (folder.isEmpty() || folder.getOriginalFilename() == null)
            throw new FolderUploadException("Folder must have name");

        try {
            String folderName = getUserParentFolder(principal) + folder.getOriginalFilename();
            InputStream inputStream = folder.getInputStream();
            folderRepository.saveFolder(inputStream, folderName);
        } catch (Exception e) {
            throw new FolderUploadException("Folder upload failed " + e.getMessage());
        }
    }
}
