package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.folder.*;
import com.thelastofus.cloudstorage.exception.*;
import com.thelastofus.cloudstorage.repository.FolderRepository;
import com.thelastofus.cloudstorage.repository.StorageRepository;
import com.thelastofus.cloudstorage.service.FolderService;
import com.thelastofus.cloudstorage.util.storage.StorageUtil;
import io.minio.Result;
import io.minio.SnowballObject;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.thelastofus.cloudstorage.util.storage.StorageUtil.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderServiceImpl implements FolderService {

    FolderRepository folderRepository;
    StorageRepository storageRepository;

    @Override
    public void upload(FolderUploadRequest folderUploadRequest) {
        List<SnowballObject> objects = new ArrayList<>();
        for (MultipartFile folder : folderUploadRequest.getFolder()) {
            if (folder.isEmpty() || folder.getOriginalFilename() == null)
                throw new FolderUploadException("Folder must have name and content");

            try {
                String folderName = buildPath(folderUploadRequest.getOwner(), folderUploadRequest.getPath(), folder.getOriginalFilename());
                InputStream inputStream = folder.getInputStream();
                long folderSize = folder.getSize();
                objects.add(StorageUtil.createSnowballObject(folderName, inputStream, folderSize));
            } catch (Exception e) {
                throw new FolderUploadException("Folder upload failed " + e.getMessage());
            }
        }
        folderRepository.saveFolder(objects);
    }

    @Override
    public void create(FolderCreateRequest folderCreateRequest) {
        String path = buildPath(folderCreateRequest.getOwner(), folderCreateRequest.getPath(), folderCreateRequest.getName()) + "/";
        try {
            folderRepository.createFolder(path);
        } catch (Exception e) {
            throw new FolderCreateException("Folder create failed " + e.getMessage());
        }
    }

    @Override
    public void remove(FolderRemoveRequest folderRemoveRequest) {
        String path = getFolderPath(folderRemoveRequest.getOwner(), folderRemoveRequest.getPath());
        createDeleteObjects(retrieveObjects(path));
    }

    @Override
    public ByteArrayResource download(FolderDownloadRequest folderDownloadRequest) {
        String path = getFolderPath(folderDownloadRequest.getOwner(), folderDownloadRequest.getPath());
        String name = folderDownloadRequest.getName();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)){
            addFilesAndFoldersToZip(zos, path, name);
            zos.close();
            return new ByteArrayResource(baos.toByteArray());
        } catch (Exception e) {
            throw new FolderDownloadException("Folder download failed " + e.getMessage());
        }
    }

    @Override
    public void rename(FolderRenameRequest folderRenameRequest) {
        String from = getFolderPath(folderRenameRequest.getOwner(), folderRenameRequest.getFrom());
        String to = getFilePathForCopy(from, folderRenameRequest.getTo());
        folderRepository.createFolder(to);

        addFilesAndFoldersToNewFolder(from, to);
        createDeleteObjects(retrieveObjects(from));
    }

    private void addFilesAndFoldersToNewFolder(String from, String to) {
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(from, false);
            for(Result<Item> result : results) {
                Item item = result.get();
                String objectName = item.objectName();
                if (item.isDir()){
                    addFilesAndFoldersToNewFolder(objectName, getNewFolderPath(objectName, to));
                } else {
                    folderRepository.copyFolder(objectName, getNewFilePath(objectName, to));
                }
            }
        } catch (Exception e) {
            throw new FolderRenameException("Fail while adding object to a new folder");
        }
    }

    private void addFilesAndFoldersToZip(ZipOutputStream zos, String path, String folderName) {
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(path, true);
            for (Result<Item> result : results) {
                Item item = result.get();
                String objectName = item.objectName();
                if (!item.isDir()) {
                    String fileName = objectName.substring(objectName.indexOf(folderName));
                    zos.putNextEntry(new ZipEntry(fileName));
                    zos.closeEntry();
                }
            }
        } catch (Exception e) {
            throw new FolderDownloadException("Folder download exception " + e.getMessage());
        }
    }

    private List<DeleteObject> retrieveObjects(String path) {
        List<DeleteObject> objects = new LinkedList<>();
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(path, true);
            for (Result<Item> result : results) {
                objects.add(StorageUtil.createDeleteObject(result.get()));
            }
        } catch (Exception e) {
            throw new FolderRemoveException("Failed to retrieve objects: " + e.getMessage());
        }
        return objects;
    }

    private void createDeleteObjects(List<DeleteObject> objects) {
        Iterable<Result<DeleteError>> results = folderRepository.removeFolder(objects);
        try {
            for (Result<DeleteError> result : results) {
                result.get();
            }
        } catch (Exception e) {
            throw new FolderRemoveException("Failed to remove folder " + e.getMessage());
        }
    }
}
