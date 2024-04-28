package com.thelastofus.cloudstorage.repository.impl;

import com.thelastofus.cloudstorage.props.MinioProperties;
import com.thelastofus.cloudstorage.repository.FileRepository;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.Iterator;


@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FileRepositoryImpl implements FileRepository {

    MinioProperties minioProperties;
    MinioClient minioClient;

    @Override
    @SneakyThrows
    public void saveFile(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }
}
