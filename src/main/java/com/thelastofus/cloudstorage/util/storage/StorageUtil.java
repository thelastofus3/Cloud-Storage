package com.thelastofus.cloudstorage.util.storage;

import io.minio.SnowballObject;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.time.LocalDateTime;

import static com.thelastofus.cloudstorage.util.size.SizeUtil.convertFromB;
import static com.thelastofus.cloudstorage.util.time.TimeUtil.getTimePattern;

@UtilityClass
public class StorageUtil {

    private static final int GMT2_TIME = 2;

    public static String getUserMainFolder(String owner, String relativePath) {
        return "user-" + owner + "-files/" + (relativePath.isEmpty() ? "" : relativePath + "/");
    }

    public static String buildPath(String owner, String relativePath, String name) {
        return getUserMainFolder(owner, relativePath) + name;
    }

    public static String getUserMainFolder(String owner) {
        return getUserMainFolder(owner,"");
    }

    public static StorageObject createStorageObject(Item item, String userFolder, String owner) {
        String objectName = item.objectName();
        String name = extractRelativePath(objectName, userFolder.length());
        String relativePath = extractRelativePath(objectName, getUserMainFolder(owner).length());
        StorageObject storageObject = createStorageObjectBase(item);

        return storageObjectBuild(storageObject, name, relativePath);
    }

    public static StorageObject createStorageSearchObject(Item item) {
        String objectName = item.objectName();
        String name = getFileOrFolderName(objectName, item.isDir());
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

    public static String getFolderPath(String owner, String relativePath) {
        String pathWithUserMainFolder = getUserMainFolder(owner, relativePath);
        String rootPathWithoutLastSlash = pathWithUserMainFolder.substring(0, pathWithUserMainFolder.length() - 1);
        return rootPathWithoutLastSlash.substring(0, rootPathWithoutLastSlash.lastIndexOf('/') + 1);
    }

    public static String getFilePath(String owner, String relativePath) {
        return getUserMainFolder(owner, relativePath).substring(0, getUserMainFolder(owner, relativePath).length() - 1);
    }

    public static String getFilePath(String owner, String relativePath, String fileType) {
        String folder = (fileType.lastIndexOf('/') != -1) ? fileType.substring(0, fileType.lastIndexOf('/')) : "";
        String rootPath = getUserMainFolder(owner, folder);
        String extension = fileType.substring(fileType.lastIndexOf('.'));
        return rootPath + relativePath + extension;
    }

    public static String getFilePathForCopy(String from, String to) {
        String rootPathWithoutLastSlash = from.substring(0,from.lastIndexOf('/'));
        return rootPathWithoutLastSlash.substring(0, rootPathWithoutLastSlash.lastIndexOf('/') + 1) + to + '/';
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

    public static String getNewFolderPath(String from, String to) {
        String newPath = from.substring(0, from.lastIndexOf('/'));
        return to + newPath.substring(newPath.lastIndexOf('/') + 1) + '/';
    }

    public static String getNewFilePath(String from, String to) {
        return to + from.substring(from.lastIndexOf('/') + 1);
    }

    private static StorageObject createStorageObjectBase(Item item) {
        String size = convertFromB(item.size());
        Boolean isDir = item.isDir();
        String lastModified = isDir ? "" : item.lastModified().plusHours(GMT2_TIME).format(getTimePattern());

        return StorageObject.builder()
                .size(size)
                .lastModified(lastModified)
                .isDir(isDir)
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

    private String extractRelativePath(String fullPath, int userFolderLength) {
        String path = fullPath.substring(userFolderLength);
        int lastSlashIndex = path.lastIndexOf('/');
        return lastSlashIndex != -1 ? path.substring(0,lastSlashIndex) : path;
    }
}