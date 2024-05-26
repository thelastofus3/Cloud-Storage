package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.util.StorageObject;
import com.thelastofus.cloudstorage.util.StorageSummary;

import java.security.Principal;
import java.util.List;

public interface StorageService {

    List<StorageObject> getAllStorageObjects(Principal principal, String currentPath);

    StorageSummary getStorageSummary(Principal principal, String currentPath);

}
