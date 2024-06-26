package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.file.FileDownloadRequest;
import com.thelastofus.cloudstorage.dto.file.FileRemoveRequest;
import com.thelastofus.cloudstorage.dto.file.FileRenameRequest;
import com.thelastofus.cloudstorage.dto.file.FileUploadRequest;
import com.thelastofus.cloudstorage.exception.FileDownloadException;
import com.thelastofus.cloudstorage.exception.FileRemoveException;
import com.thelastofus.cloudstorage.exception.FileRenameException;
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

import static com.thelastofus.cloudstorage.util.storage.StorageUtil.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileServiceImpl implements FileService {

    FileRepository fileRepository;

    @Override
    public void upload(FileUploadRequest fileUploadRequest) {
        try {
            MultipartFile file = fileUploadRequest.getFile();
            if (file.isEmpty() || file.getOriginalFilename() == null || file.getSize() == 0)
                throw new FileUploadException("File must have name and content");

            String path = buildPath(fileUploadRequest.getOwner(), fileUploadRequest.getPath(), file.getOriginalFilename());
            InputStream inputStream = file.getInputStream();
            fileRepository.saveFile(inputStream, path);
        } catch (Exception e) {
            throw new FileUploadException("File upload failed " + e.getMessage());
        }
    }

    @Override
    public void remove(FileRemoveRequest fileRemoveRequest) {
        try {
            String path = getFilePath(fileRemoveRequest.getOwner(), fileRemoveRequest.getPath());

            fileRepository.removeFile(path);
        } catch (Exception e) {
            throw new FileRemoveException("File remove failed: " + e.getMessage());
        }
    }

    @Override
    public ByteArrayResource download(FileDownloadRequest fileDownloadRequest) {
        String path = getFilePath(fileDownloadRequest.getOwner(), fileDownloadRequest.getPath());
        try (InputStream inputStream = fileRepository.downloadFile(path)) {
            return new ByteArrayResource(inputStream.readAllBytes());
        } catch (Exception e) {
            throw new FileDownloadException("File download failed: " + e.getMessage());
        }
    }

    @Override
    public void rename(FileRenameRequest fileRenameRequest) {
        String from = getFilePath(fileRenameRequest.getOwner(), fileRenameRequest.getFrom());
        String to = getFilePath(fileRenameRequest.getOwner(), fileRenameRequest.getTo(), fileRenameRequest.getFrom());

        renameFile(from, to);
    }

    private void renameFile(String from, String to) {
        try {
            fileRepository.copyFile(from, to);
            fileRepository.removeFile(from);
        } catch (Exception e) {
            throw new FileRenameException("File rename failed: " + e.getMessage());
        }
    }
}
