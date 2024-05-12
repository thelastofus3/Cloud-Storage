package com.thelastofus.cloudstorage.repository;

import io.minio.Result;
import io.minio.messages.Item;

import java.security.Principal;

public interface StorageRepository {

    Iterable<Result<Item>> getObjects(Principal principal, String currentPath);
}
