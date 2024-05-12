package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.StorageObject;
import com.thelastofus.cloudstorage.dto.StorageSummary;

import java.security.Principal;
import java.util.List;

public interface StorageService {

    List<StorageObject> getAllStorageObjects(Principal principal, String currentPath);

    StorageSummary getStorageSummary(Principal principal, String currentPath);

}
