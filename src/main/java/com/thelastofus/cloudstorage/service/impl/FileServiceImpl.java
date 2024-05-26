package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.file.FileDownloadRequest;
import com.thelastofus.cloudstorage.dto.file.FileRemoveRequest;
import com.thelastofus.cloudstorage.dto.file.FileUploadRequest;
import com.thelastofus.cloudstorage.exception.FileDownloadException;
import com.thelastofus.cloudstorage.exception.FileRemoveException;
import com.thelastofus.cloudstorage.exception.FileUploadException;
import com.thelastofus.cloudstorage.repository.FileRepository;
import com.thelastofus.cloudstorage.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.Principal;

import static com.thelastofus.cloudstorage.util.StorageUtil.getFilePath;
import static com.thelastofus.cloudstorage.util.StorageUtil.getUserMainFolder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FileServiceImpl implements FileService {

    FileRepository fileRepository;

    @Override
    public void upload(FileUploadRequest fileUploadRequest, Principal principal) {
        MultipartFile file = fileUploadRequest.getFile();
        if (file.isEmpty() || file.getOriginalFilename() == null || file.getSize() == 0)
            throw new FileUploadException("File must have name and content");

        try {
            String path = getUserMainFolder(principal, fileUploadRequest.getPath()) + file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            fileRepository.saveFile(inputStream, path);
        } catch (Exception e) {
            throw new FileUploadException("File upload failed " + e.getMessage());
        }
    }

    @Override
    public void remove(FileRemoveRequest fileRemoveRequest, Principal principal) {
        String path = getFilePath(principal, fileRemoveRequest);
        try {
            fileRepository.removeFile(path);
        } catch (Exception e) {
            throw new FileRemoveException("File remove failed " + e.getMessage());
        }
    }

    @Override
    public ByteArrayResource download(FileDownloadRequest fileDownloadRequest, Principal principal) {
        String fileName = fileDownloadRequest.getFileName();
        String path = getFilePath(principal, fileDownloadRequest);
        try {
            InputStream inputStream = fileRepository.downloadFile(fileName, path);
            return new ByteArrayResource(inputStream.readAllBytes());
        } catch (Exception e) {
            throw new FileDownloadException("File download failed " + e.getMessage());
        }
    }
}
