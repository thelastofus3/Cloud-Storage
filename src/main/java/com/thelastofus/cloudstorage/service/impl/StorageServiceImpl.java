package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.StorageObject;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.thelastofus.cloudstorage.util.MinioUtil.getUserParentFolder;
import static com.thelastofus.cloudstorage.util.TimeUtil.getTimePattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class StorageServiceImpl implements StorageService {

    private static final int GMT2_TIME = 2;

    StorageRepository storageRepository;

    @Override
    public List<StorageObject> getAllStorageObjects(Principal principal) {
        String userFolder = getUserParentFolder(principal);
        List<StorageObject> storageObjects = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(principal);
            for (Result<Item> result : results) {
                Item item = result.get();
                storageObjects.add(createStorageObject(item, userFolder));
            }
        } catch (Exception e) {
            throw new NoSuchFilesException("Failed to get files for user: " + principal.getName() + ", error: " + e.getMessage());
        }
        return storageObjects;
    }

    private StorageObject createStorageObject(Item item, String userFolder) {
        String objectName = item.objectName();
        String relativePath = objectName.substring(userFolder.length());
        String size = String.valueOf(item.size());
        String lastModified = item.isDir() ? null : item.lastModified().plusHours(GMT2_TIME).format(getTimePattern());

        return StorageObject.builder()
                .path(relativePath)
                .size(size)
                .lastModified(lastModified)
                .build();
    }

}
