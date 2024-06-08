package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.service.UserService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.thelastofus.cloudstorage.util.StorageUtil.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class StorageServiceImpl implements StorageService {

    StorageRepository storageRepository;
    UserService userService;

    @Override
    public List<StorageObject> getStorageObjects(String currentPath, Principal principal) {
        String userMainFolder = getUserMainFolder(principal, currentPath);
        int userMainFolderLength = getUserMainFolderLength(principal);
        List<StorageObject> storageObjects = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(userMainFolder, false);
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
    public StorageSummary getStorageSummary(String path, Principal principal) {
        String userMainFolder = getUserMainFolder(principal);
        LocalDateTime createdAt = userService.getCreatedAt(principal.getName());
        int countOfObjects = getCountOfObjects(userMainFolder, principal);

        return createStorageSummary(countOfObjects, path, createdAt);
    }

    @Override
    public List<StorageObject> search(String query, Principal principal) {
        String userMainFolder = getUserMainFolder(principal);
        List<StorageObject> storageObjects = new ArrayList<>();
        findObjectsRecursively(query, userMainFolder, storageObjects, principal);
        return storageObjects;
    }

    private void findObjectsRecursively(String query, String currentPath, List<StorageObject> storageObjects, Principal principal) {
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(currentPath, false);
            for(Result<Item> result : results) {
                Item item = result.get();
                String objectName = getFileOrFolderName(item.objectName(), item.isDir());

                if (objectName.equals(query))
                    storageObjects.add(createStorageSearchObject(item, item.isDir()));

                if (item.isDir())
                    findObjectsRecursively(query, item.objectName(), storageObjects, principal);
            }
        } catch (Exception e) {
            throw new NoSuchFilesException("Failed to get files for user: " + principal.getName() + ", error: " + e.getMessage());
        }
    }

    private int getCountOfObjects(String userFolder, Principal principal) {
        int countOfObjects = 0;
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(userFolder, true);
            for (Result<Item> ignored : results) {
                countOfObjects++;
            }
        }catch (Exception e) {
            throw new NoSuchFilesException("Failed to get information about files for user: " + principal.getName() + ", error: " + e.getMessage());
        }

        return countOfObjects;
    }
}