package com.thelastofus.cloudstorage.repository;

import org.springframework.stereotype.Repository;

import java.io.InputStream;


public interface FileRepository {

    void saveFile(InputStream inputStream, String filename);
}
