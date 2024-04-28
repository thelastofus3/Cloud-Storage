package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.FileUploadRequest;
import com.thelastofus.cloudstorage.exception.FileUploadException;
import com.thelastofus.cloudstorage.exception.NoSuchFilesException;
import com.thelastofus.cloudstorage.repository.FileRepository;
import com.thelastofus.cloudstorage.service.FileService;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FileServiceImpl implements FileService {

    FileRepository fileRepository;

    @Override
    public void upload(FileUploadRequest fileUploadRequest, Principal principal) {
        MultipartFile file = fileUploadRequest.getFile();
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new FileUploadException("File must have name");
        }

        try {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            fileRepository.saveFile(inputStream, fileName);
        } catch (Exception e) {
            throw new FileUploadException("File upload failed " + e.getMessage());
        }
    }

}
