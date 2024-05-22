package com.thelastofus.cloudstorage.repository;

import io.minio.SnowballObject;

import java.io.InputStream;
import java.util.List;

public interface FolderRepository {
    void saveFolder(List<SnowballObject> objects);

    void createFolder(String folderName);
}
