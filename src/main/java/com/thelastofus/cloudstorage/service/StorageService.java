package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.util.storage.StorageObject;
import com.thelastofus.cloudstorage.util.storage.StorageSummary;

import java.security.Principal;
import java.util.List;

public interface StorageService {

    List<StorageObject> getStorageObjects(String currentPath, Principal principal);

    StorageSummary getStorageSummary(String currentPath, Principal principal);

    List<StorageObject> search(String query, Principal principal);
}
