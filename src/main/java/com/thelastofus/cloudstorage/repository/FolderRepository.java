package com.thelastofus.cloudstorage.repository;

import io.minio.Result;
import io.minio.SnowballObject;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;

import java.io.InputStream;
import java.util.List;

public interface FolderRepository {
    void saveFolder(List<SnowballObject> objects);

    void createFolder(String name);

    void copyFolder(String from, String to);

    Iterable<Result<DeleteError>> removeFolder(List<DeleteObject> objects);

    InputStream downloadFolder(String path);

}
