package com.thelastofus.cloudstorage.repository.impl;

import com.thelastofus.cloudstorage.props.MinioProperties;
import com.thelastofus.cloudstorage.repository.FileRepository;
import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
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
    public void saveFile(InputStream inputStream, String path) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(path)
                .build());
    }

    @Override
    @SneakyThrows
    public void removeFile(String path) {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(path)
                .build());
    }

    @Override
    @SneakyThrows
    public void downloadFile(String fileName, String path) {
        minioClient.downloadObject(DownloadObjectArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(path)
                .filename(fileName)
                .build());
    }
}
