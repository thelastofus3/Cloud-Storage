package com.thelastofus.cloudstorage.repository;

import io.minio.Result;
import io.minio.messages.Item;

public interface StorageRepository {

    Iterable<Result<Item>> getObjects();
}
