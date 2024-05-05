package com.thelastofus.cloudstorage.repository.impl;

import com.thelastofus.cloudstorage.props.MinioProperties;
import com.thelastofus.cloudstorage.repository.StorageRepository;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.security.Principal;

import static com.thelastofus.cloudstorage.util.MinioUtil.getUserParentFolder;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class StorageRepositoryImpl implements StorageRepository {

    MinioProperties minioProperties;
    MinioClient minioClient;

    @Override
    public Iterable<Result<Item>> getObjects(Principal principal) {
        ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                .bucket(minioProperties.getBucket())
                .prefix(getUserParentFolder(principal))
                .recursive(true)
                .build();

        return minioClient.listObjects(listObjectsArgs);
    }
}
