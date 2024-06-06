package com.thelastofus.cloudstorage.repository.impl;

import com.thelastofus.cloudstorage.props.MinioProperties;
import com.thelastofus.cloudstorage.repository.FileRepository;
import io.minio.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.io.InputStream;


@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FileRepositoryImpl implements FileRepository {

    MinioProperties minioProperties;
    MinioClient minioClient;

    @Override
    @SneakyThrows
    public void saveFile(InputStream inputStream, String name) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(name)
                .build());
    }

    @Override
    @SneakyThrows
    public void removeFile(String name) {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(name)
                .build());
    }

    @Override
    @SneakyThrows
    public InputStream downloadFile(String path) {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(path)
                .build());
    }

    @Override
    @SneakyThrows
    public void copyFile(String from, String to) {
            minioClient.copyObject(CopyObjectArgs.builder()
                    .source(CopySource.builder()
                            .bucket(minioProperties.getBucket())
                            .object(from)
                            .build())
                    .bucket(minioProperties.getBucket())
                    .object(to)
            .build());
    }
}
