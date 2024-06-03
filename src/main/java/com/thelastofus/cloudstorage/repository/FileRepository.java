package com.thelastofus.cloudstorage.repository;

import io.minio.Result;
import io.minio.messages.Item;

import java.io.InputStream;


public interface FileRepository {

    void saveFile(InputStream inputStream, String fileName);

    void removeFile(String fileName);

    InputStream downloadFile(String path);

}
