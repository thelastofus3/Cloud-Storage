package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.FolderUploadRequest;
import com.thelastofus.cloudstorage.exception.FolderUploadException;
import com.thelastofus.cloudstorage.repository.FolderRepository;
import com.thelastofus.cloudstorage.service.FolderService;
import com.thelastofus.cloudstorage.util.StorageUtil;
import io.minio.SnowballObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.thelastofus.cloudstorage.util.StorageUtil.getUserMainFolder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderServiceImpl implements FolderService {

    FolderRepository folderRepository;

    @Override
    public void upload(FolderUploadRequest folderUploadRequest, Principal principal, String currentPath) {
        List<SnowballObject> objects = new ArrayList<>();
        for (MultipartFile folder : folderUploadRequest.getFolder()) {
            if (folder.isEmpty() || folder.getOriginalFilename() == null)
                throw new FolderUploadException("Folder must have name and content");

            try {
                String folderName = getUserMainFolder(principal, currentPath) + folder.getOriginalFilename();
                InputStream inputStream = folder.getInputStream();
                long folderSize = folder.getSize();
                objects.add(StorageUtil.createSnowballObject(folderName, inputStream, folderSize));
            } catch (Exception e) {
                throw new FolderUploadException("Folder upload failed " + e.getMessage());
            }
        }
        folderRepository.saveFolder(objects);
    }
}
