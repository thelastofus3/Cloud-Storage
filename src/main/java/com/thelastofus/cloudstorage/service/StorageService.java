package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.util.StorageObject;
import com.thelastofus.cloudstorage.util.StorageSummary;

import java.security.Principal;
import java.util.List;

public interface StorageService {

    List<StorageObject> getAllStorageObjects(String currentPath, Principal principal);

    StorageSummary getStorageSummary(String currentPath, Principal principal);

    List<StorageObject> search(String query, Principal principal);
}
