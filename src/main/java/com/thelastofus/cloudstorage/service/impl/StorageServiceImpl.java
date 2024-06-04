package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.model.User;
import com.thelastofus.cloudstorage.repository.UserRepository;
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
import java.util.Optional;

import static com.thelastofus.cloudstorage.util.StorageUtil.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class StorageServiceImpl implements StorageService {

    StorageRepository storageRepository;
    UserService userService;

    @Override
    public List<StorageObject> getAllStorageObjects(Principal principal, String currentPath) {
        String userMainFolder = getUserMainFolder(principal, currentPath);
        int userMainFolderLength = getUserMainFolderLength(principal);
        List<StorageObject> storageObjects = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(userMainFolder);
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

        String userFolder = getUserMainFolder(principal);
        LocalDateTime createdAt = userService.getCreatedAt(principal.getName());
        int countOfObjects = getCountOfObjects(principal,userFolder);

        return createStorageSummary(countOfObjects, path, createdAt);
    }

    private int getCountOfObjects(Principal principal, String userFolder) {
        int countOfObjects = 0;
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(userFolder);
            for (Result<Item> result : results) {
                Item item = result.get();
                countOfObjects++;
                if (item.isDir()) {
                    countOfObjects += getCountOfObjects(principal, item.objectName());
                }
            }
        }catch (Exception e) {
            throw new NoSuchFilesException("Failed to get information about files for user: " + principal.getName() + ", error: " + e.getMessage());
        }

        return countOfObjects;
    }

}
