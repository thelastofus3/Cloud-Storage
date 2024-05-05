package com.thelastofus.cloudstorage.service.impl;

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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class StorageServiceImpl implements StorageService {

    StorageRepository storageRepository;

    @Override
    public List<String> getAllFiles(Principal principal) {
        Iterable<Result<Item>> results = storageRepository.getObjects(principal);
        List<String> fileNames = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                Item item = result.get();
                fileNames.add(item.objectName());
            }
        } catch (Exception e) {
            throw new NoSuchFilesException("Failed to get files " + e.getMessage());
        }

        return fileNames;
    }
}
