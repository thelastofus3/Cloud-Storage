package com.thelastofus.cloudstorage.util;

import com.thelastofus.cloudstorage.dto.file.FileRequest;
import com.thelastofus.cloudstorage.dto.folder.FolderRemoveRequest;
import io.minio.SnowballObject;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.time.LocalDateTime;

import static com.thelastofus.cloudstorage.util.TimeUtil.getTimePattern;

@UtilityClass
public class StorageUtil {

    private static final int GMT2_TIME = 2;

    private static final BigDecimal BToKiB_DIVIDER = new BigDecimal("1024");

    public static String getUserMainFolder(Principal principal, String currentPath) {

        String userFolder = "user-" + principal.getName() + "-files/" ;
        if (!currentPath.isEmpty()) {
            userFolder += currentPath + "/";
        }
        return userFolder;
    }
    public static String buildFolderPath(Principal principal, String path, String name) {
        return getUserMainFolder(principal, path) + name;
    }

    public static String getUserMainFolder(Principal principal) {
        return getUserMainFolder(principal,"");
    }

    public static int getUserMainFolderLength(Principal principal) {
        return getUserMainFolder(principal).length() ;
    }

    public static StorageObject createStorageObject(Item item, String userFolder, int userFolderLength) {
        String objectName = item.objectName();
        String name = extractNameFromPath(objectName, userFolder.length());
        String relativePath = extractNameFromPath(objectName, userFolderLength);
        StorageObject storageObject = createStorageObjectBase(item);

        return storageObjectBuild(storageObject, name, relativePath);
    }

    public static StorageObject createStorageSearchObject(Item item, boolean isDir) {
        String objectName = item.objectName();
        String name = getFileOrFolderName(objectName, isDir);
        String path = objectName.substring(objectName.indexOf('/'));
        StorageObject storageObject = createStorageObjectBase(item);

        return storageObjectBuild(storageObject, name, path);
    }

    public static SnowballObject createSnowballObject(String path, InputStream inputStream, long folderSize) {
        return new SnowballObject(path, inputStream, folderSize, null);
    }

    public static StorageSummary createStorageSummary(int countOfObjects, String path, LocalDateTime localDateTime) {
        return StorageSummary.builder()
                .countOfObjects(countOfObjects)
                .currentPath(path)
                .creationDate(localDateTime)
                .build();
    }

    public static DeleteObject createDeleteObject(Item item) {
        return new DeleteObject(item.objectName());
    }

    public static String getFolderPath(Principal principal, FolderRemoveRequest folderRemoveRequest) {
        return getFolderPath(principal, folderRemoveRequest.getPath());
    }

    public static String getFolderPath(Principal principal, String relativePath) {
        return getParentFolder(getUserMainFolder(principal, relativePath));
    }

    public static String getFilePath(Principal principal, FileRequest fileRequest) {
        return getFilePath(principal, fileRequest.getPath());
    }

    public static String getFilePath(Principal principal, String relativePath) {
        return getUserMainFolder(principal, relativePath).substring(0, getUserMainFolder(principal, relativePath).length() - 1);
    }

    public static String getFilePath(Principal principal, String relativePath, String fileType) {
        String folder = (fileType.lastIndexOf('/') != -1) ? fileType.substring(0, fileType.lastIndexOf('/')) : "";
        String rootPath = getUserMainFolder(principal, folder);
        String extension = fileType.substring(fileType.lastIndexOf('.'));
        return rootPath + relativePath + extension;
    }

    public static String getFileOrFolderName(String path, boolean isDir) {
        if (!isDir) {
            String file = path.substring(path.lastIndexOf('/') + 1);
            return file.lastIndexOf('.') != -1 ? file.substring(0, file.lastIndexOf('.')) : file;
        } else {
            String folder = path.substring(0, path.lastIndexOf('/'));
            return folder.substring(folder.lastIndexOf('/') + 1);
        }
    }

    private static StorageObject createStorageObjectBase(Item item) {
        String size = String.valueOf(convertFromBToKiB(item));
        String lastModified = item.isDir() ? null : item.lastModified().plusHours(GMT2_TIME).format(getTimePattern());

        return StorageObject.builder()
                .size(size)
                .lastModified(lastModified)
                .build();
    }

    private static StorageObject storageObjectBuild(StorageObject storageObject, String name, String path) {
        return StorageObject.builder()
                .name(name)
                .path(path)
                .size(storageObject.getSize())
                .lastModified(storageObject.getLastModified())
                .build();
    }

    private BigDecimal convertFromBToKiB(Item item) {
        BigDecimal sizeInB = new BigDecimal(item.size());
        return sizeInB.divide(BToKiB_DIVIDER,1, RoundingMode.HALF_UP);
    }

    private String extractNameFromPath(String fullPath, int userFolderLength) {
        String path = fullPath.substring(userFolderLength);
        int lastSlashIndex = path.lastIndexOf('/');
        return lastSlashIndex != -1 ? path.substring(0,lastSlashIndex) : path;
    }

    private String getParentFolder(String path) {
        String rootFolderForDelete = path.substring(0, path.length() - 1);
        return rootFolderForDelete.substring(0, rootFolderForDelete.lastIndexOf('/') + 1);
    }
}