package com.thelastofus.cloudstorage.repository.impl;

import com.thelastofus.cloudstorage.props.MinioProperties;
import com.thelastofus.cloudstorage.repository.FolderRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.SnowballObject;
import io.minio.UploadSnowballObjectsArgs;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

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
    public void saveFolder(List<SnowballObject> objects) {
        minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                .bucket(minioProperties.getBucket())
                .objects(objects)
                .build());
    }
}
