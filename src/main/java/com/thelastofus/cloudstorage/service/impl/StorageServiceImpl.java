package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.util.StorageObject;
import com.thelastofus.cloudstorage.util.StorageSummary;
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
        String userMainFolder = getUserMainFolder(principal, currentPath);
        int userMainFolderLength = getUserMainFolderLength(principal);
        List<StorageObject> storageObjects = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(principal, userMainFolder);
            for (Result<Item> result : results) {
                Item item = result.get();

                if (item.objectName().startsWith(userMainFolder))
                    storageObjects.add(createStorageObject(item, userMainFolder,userMainFolderLength));

            }
        } catch (Exception e) {
            throw new NoSuchFilesException("Failed to get files for user: " + principal.getName() + ", error: " + e.getMessage());
        }
        return storageObjects;
    }

    @Override
    public StorageSummary getStorageSummary(Principal principal, String path) {
        int countOfObjects = 0;
        String userFolder = getUserMainFolder(principal, path);
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(principal, userFolder);
            for (Result<Item> ignored : results) {
                countOfObjects++;
            }
        }catch (Exception e) {
            throw new NoSuchFilesException("Failed to get information about files for user: " + principal.getName() + ", error: " + e.getMessage());
        }

        return createStorageSummary(countOfObjects, path);
    }

}
