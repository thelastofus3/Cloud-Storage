package com.thelastofus.cloudstorage.util;

import com.thelastofus.cloudstorage.dto.file.FileRequest;
import com.thelastofus.cloudstorage.dto.folder.FolderRemoveRequest;
import io.minio.SnowballObject;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.parameters.P;

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

    public static String getUserMainFolder(Principal principal) {
        return getUserMainFolder(principal,"");
    }

    public static int getUserMainFolderLength(Principal principal) {
        return getUserMainFolder(principal).length() ;
    }

    public static StorageObject createStorageObject(Item item, String userFolder, int userFolderLength) {
        String objectName = item.objectName();
        String name = extractNameFromPath(objectName, userFolder.length());
        String relativePath = extractNameFromPath(objectName, userFolderLength) ;
        String size = String.valueOf(convertFromBToKiB(item));
        String lastModified = item.isDir() ? null : item.lastModified().plusHours(GMT2_TIME).format(getTimePattern());

        return StorageObject.builder()
                .name(name)
                .path(relativePath)
                .size(size)
                .lastModified(lastModified)
                .build();
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

    private BigDecimal convertFromBToKiB(Item item) {
        BigDecimal sizeInB = new BigDecimal(item.size());
        return sizeInB.divide(BToKiB_DIVIDER,1, RoundingMode.HALF_UP);
    }

    private String extractNameFromPath(String fullPath, int userFolderLength) {
        String path = fullPath.substring(userFolderLength);
        int index = path.lastIndexOf('/');
        return index != -1 ? path.substring(0,index) : path;
    }

    private String getParentFolder(String path) {
        String rootFolderForDelete = path.substring(0, path.length() - 1);
        return rootFolderForDelete.substring(0, rootFolderForDelete.lastIndexOf('/') + 1);
    }

}