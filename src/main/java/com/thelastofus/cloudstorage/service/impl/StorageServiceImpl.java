package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.StorageObject;
import com.thelastofus.cloudstorage.dto.StorageSummary;
import com.thelastofus.cloudstorage.exception.NoSuchFilesException;
import com.thelastofus.cloudstorage.repository.StorageRepository;
import com.thelastofus.cloudstorage.service.StorageService;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.thelastofus.cloudstorage.util.StorageUtil.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class StorageServiceImpl implements StorageService {

    StorageRepository storageRepository;

    @Override
    public List<StorageObject> getAllStorageObjects(Principal principal, String currentPath) {
        String userFolder = getUserFolder(principal, currentPath);
        int userFolderLength = userFolderLength(principal);
        List<StorageObject> storageObjects = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(principal, userFolder);
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.objectName().startsWith(userFolder)) {
                    storageObjects.add(createStorageObject(item, userFolder,userFolderLength));
                }
            }
        } catch (Exception e) {
            throw new NoSuchFilesException("Failed to get files for user: " + principal.getName() + ", error: " + e.getMessage());
        }
        return storageObjects;
    }

    @Override
    public StorageSummary getStorageSummary(Principal principal, String currentPath) {
        int countOfObjects = 0;
        String userFolder = getUserFolder(principal,currentPath);
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(principal, userFolder);
            for (Result<Item> ignored : results) {
                countOfObjects++;
            }
        }catch (Exception e) {
            throw new NoSuchFilesException("Failed to get information about files for user: " + principal.getName() + ", error: " + e.getMessage());
        }

        return createStorageSummary(countOfObjects);
    }

}
