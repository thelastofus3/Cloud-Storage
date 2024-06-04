package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.folder.FolderCreateRequest;
import com.thelastofus.cloudstorage.dto.folder.FolderDownloadRequest;
import com.thelastofus.cloudstorage.dto.folder.FolderRemoveRequest;
import com.thelastofus.cloudstorage.dto.folder.FolderUploadRequest;
import com.thelastofus.cloudstorage.exception.FolderCreateException;
import com.thelastofus.cloudstorage.exception.FolderDownloadException;
import com.thelastofus.cloudstorage.exception.FolderRemoveException;
import com.thelastofus.cloudstorage.exception.FolderUploadException;
import com.thelastofus.cloudstorage.repository.FolderRepository;
import com.thelastofus.cloudstorage.repository.StorageRepository;
import com.thelastofus.cloudstorage.service.FolderService;
import com.thelastofus.cloudstorage.util.StorageUtil;
import io.minio.Result;
import io.minio.SnowballObject;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.thelastofus.cloudstorage.util.StorageUtil.getFolderPath;
import static com.thelastofus.cloudstorage.util.StorageUtil.getUserMainFolder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderServiceImpl implements FolderService {

    FolderRepository folderRepository;

    StorageRepository storageRepository;

    @Override
    public void upload(FolderUploadRequest folderUploadRequest, Principal principal) {
        List<SnowballObject> objects = new ArrayList<>();
        for (MultipartFile folder : folderUploadRequest.getFolder()) {
            if (folder.isEmpty() || folder.getOriginalFilename() == null)
                throw new FolderUploadException("Folder must have name and content");

            try {
                String folderName = getUserMainFolder(principal, folderUploadRequest.getPath()) + folder.getOriginalFilename();
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
    public void create(FolderCreateRequest folderCreateRequest, Principal principal) {
        String path = getUserMainFolder(principal, folderCreateRequest.getPath()) + folderCreateRequest.getName() + "/";
        try {
            folderRepository.createFolder(path);
        } catch (Exception e) {
            throw new FolderCreateException("Folder create failed " + e.getMessage());
        }
    }

    @Override
    public void remove(FolderRemoveRequest folderRemoveRequest, Principal principal) {
        String path = getFolderPath(principal, folderRemoveRequest);
        List<DeleteObject> objects = retrieveObjects(path);

        createDeleteObjects(objects);
    }

    @Override
    public ByteArrayResource download(FolderDownloadRequest folderDownloadRequest, Principal principal) {
        String path = getUserMainFolder(principal, folderDownloadRequest.getPath().substring(0, folderDownloadRequest.getPath().length() - folderDownloadRequest.getName().length() - 1));
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

    private void addFilesAndFoldersToZip(ZipOutputStream zos, String path, String folderName) {
        Iterable<Result<Item>> results = storageRepository.getObjects(path);
        try {
            for (Result<Item> result : results) {
                Item item = result.get();
                String objectName = item.objectName();
                if (item.isDir()) {
                    addFilesAndFoldersToZip(zos, objectName , folderName);
                } else {
                    addFilesToZip(zos, objectName, folderName);
                }
            }
        } catch (Exception e) {
            throw new FolderDownloadException("Folder download exception " + e.getMessage());
        }
    }

    private void addFilesToZip(ZipOutputStream zos, String objectName, String folderName) {
        try (InputStream inputStream = folderRepository.downloadFolder(objectName)) {
            int folderNameIndex = objectName.indexOf(folderName);
            String fileName = objectName.substring(folderNameIndex);
            zos.putNextEntry(new ZipEntry(fileName));
            IOUtils.copy(inputStream, zos);
            zos.closeEntry();
        }catch (Exception e) {
            throw new FolderDownloadException("Error while adding file to zip " + e.getMessage());
        }

    }

    private List<DeleteObject> retrieveObjects(String path) {
        List<DeleteObject> objects = new LinkedList<>();
        try {
            Iterable<Result<Item>> results = storageRepository.getObjects(path);
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir()) {
                    objects.addAll(retrieveObjects(item.objectName()));
                } else {
                    objects.add(StorageUtil.createDeleteObject(item));
                }
            }
        } catch (Exception e) {
            throw new FolderRemoveException("Failed to retrieve objects: " + e.getMessage());
        }
        return objects;
    }
    private void createDeleteObjects(List<DeleteObject> objects) {
        Iterable<Result<DeleteError>> results = folderRepository.removeFolder(objects);
        results.forEach(deleteErrorResult -> {
            try {
                deleteErrorResult.get();
            }
            catch (Exception e) {
                throw new FolderRemoveException("Failed to remove folder " + e.getMessage());
            }
        });
    }
}
