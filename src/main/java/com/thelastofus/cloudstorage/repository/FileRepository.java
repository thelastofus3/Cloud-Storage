package com.thelastofus.cloudstorage.repository;

import java.io.InputStream;


public interface FileRepository {

    void saveFile(InputStream inputStream, String name);

    void removeFile(String name);

    InputStream downloadFile(String path);

    void copyFile(String from, String to);
}
