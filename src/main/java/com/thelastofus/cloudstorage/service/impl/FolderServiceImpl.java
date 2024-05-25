package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.FolderCreateRequest;
import com.thelastofus.cloudstorage.dto.FolderRemoveRequest;
import com.thelastofus.cloudstorage.dto.FolderUploadRequest;
import com.thelastofus.cloudstorage.exception.FolderCreateException;
import com.thelastofus.cloudstorage.exception.FolderRemoveException;
import com.thelastofus.cloudstorage.exception.FolderUploadException;
import com.thelastofus.cloudstorage.repository.FolderRepository;
import com.thelastofus.cloudstorage.repository.StorageRepository;
import com.thelastofus.cloudstorage.service.FolderService;
import com.thelastofus.cloudstorage.util.StorageUtil;
import io.minio.Result;
import io.minio.SnowballObject;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.thelastofus.cloudstorage.util.StorageUtil.getUserMainFolder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderServiceImpl implements FolderService {

    FolderRepository folderRepository;

    StorageRepository storageRepository;

    @Override
    public void upload(FolderUploadRequest folderUploadRequest, Principal principal) {
        List<SnowballObject> objects = new ArrayList<>();
        for (MultipartFile folder : folderUploadRequest.getFolder()) {
            if (folder.isEmpty() || folder.getOriginalFilename() == null)
                throw new FolderUploadException("Folder must have name and content");

            try {
                String folderName = getUserMainFolder(principal, folderUploadRequest.getPath()) + folder.getOriginalFilename();
                InputStream inputStream = folder.getInputStream();
                long folderSize = folder.getSize();
                objects.add(StorageUtil.createSnowballObject(folderName, inputStream, folderSize));
            } catch (Exception e) {
                throw new FolderUploadException("Folder upload failed " + e.getMessage());
            }
        }
        folderRepository.saveFolder(objects);
    }

    @Override
    public void create(FolderCreateRequest folderCreateRequest, Principal principal) {
        String folderName = getUserMainFolder(principal, folderCreateRequest.getPath()) + folderCreateRequest.getName() + "/";
        try {
            folderRepository.createFolder(folderName);
        } catch (Exception e) {
            throw new FolderCreateException("Folder create failed " + e.getMessage());
        }
    }

    @Override
    public void remove(FolderRemoveRequest folderRemoveRequest, Principal principal) {
        List<DeleteObject> objects = new LinkedList<>();
        String path = getUserMainFolder(principal, folderRemoveRequest.getPath());
        String convert = path.substring(0, path.length() - 1);
        String fullPath = convert.substring(0, convert.lastIndexOf('/') + 1);
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(principal, fullPath);
            for (Result<Item> result : results) {
                Item item = result.get();
                objects.add(StorageUtil.createDeleteObject(item));
            }
        } catch (Exception e) {
            throw new FolderRemoveException("Failed to remove folder " + e.getMessage());
        }
        Iterable<Result<DeleteError>> results = folderRepository.removeFolder(objects);
        results.forEach(deleteErrorResult -> {
            try {
                deleteErrorResult.get();
            }
            catch (Exception e) {
                throw new FolderRemoveException("Failed to remove folder " + e.getMessage());
            }
        });
    }
}
