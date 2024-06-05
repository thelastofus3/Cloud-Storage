package com.thelastofus.cloudstorage.repository.impl;

import com.thelastofus.cloudstorage.props.MinioProperties;
import com.thelastofus.cloudstorage.repository.FolderRepository;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;


@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderRepositoryImpl implements FolderRepository {

    MinioProperties minioProperties;
    MinioClient minioClient;

    @Override
    @SneakyThrows
    public void saveFolder(List<SnowballObject> paths) {
        minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                .bucket(minioProperties.getBucket())
                .objects(paths)
                .build());
    }

    @Override
    @SneakyThrows
    public void createFolder(String name) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(new ByteArrayInputStream(new byte[] {}), 0, -1)
                .bucket(minioProperties.getBucket())
                .object(name)
                .build());
    }

    @Override
    @SneakyThrows
    public void copyFolder(String from, String to) {
        minioClient.copyObject(CopyObjectArgs.builder()
                .source(CopySource.builder()
                        .bucket(minioProperties.getBucket())
                        .object(from)
                        .build())
                .bucket(minioProperties.getBucket())
                .object(to)
        .build());
    }

    @Override
    @SneakyThrows
    public Iterable<Result<DeleteError>> removeFolder(List<DeleteObject> paths) {
        return minioClient.removeObjects(RemoveObjectsArgs.builder()
                .bucket(minioProperties.getBucket())
                .objects(paths)
                .build());
    }

    @Override
    @SneakyThrows
    public InputStream downloadFolder(String path) {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(path)
                .build());
    }



}
