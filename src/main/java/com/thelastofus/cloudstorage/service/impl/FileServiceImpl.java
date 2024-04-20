package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import com.thelastofus.cloudstorage.exception.FileUploadException;
import com.thelastofus.cloudstorage.props.MinioProperties;
import com.thelastofus.cloudstorage.service.FileService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FileServiceImpl implements FileService {

    MinioClient minioClient;
    MinioProperties minioProperties;

    @Override
    public void upload(FileUploadRequest fileUploadRequest) {
        try {
            createBucket();
        }catch (Exception e) {
            throw new FileUploadException("File upload failed " + e.getMessage());
        }
        MultipartFile file = fileUploadRequest.getFile();

        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new FileUploadException("Image must have name");
        }
        String fileName = generateFileName(file);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        }catch (Exception e) {
            throw new FileUploadException("File upload failed " + e.getMessage());
        }
        saveFile(inputStream,fileName);
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(MultipartFile file) {
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveFile(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }
}
