package com.thelastofus.cloudstorage.repository;

import java.io.InputStream;

public interface FolderRepository {
    void saveFolder(InputStream inputStream, String fileName);
}
