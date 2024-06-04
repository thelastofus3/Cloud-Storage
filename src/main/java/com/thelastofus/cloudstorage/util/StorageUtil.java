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
            userFolder = userFolder + currentPath + "/";
        }
        return userFolder;
    }

    public static String getUserMainFolder(Principal principal) {
        return "user-" + principal.getName() + "-files/";
    }

    public static int getUserMainFolderLength(Principal principal) {
        return ("user-" + principal.getName() + "-files/").length() ;
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
        return new SnowballObject(
                path,
                inputStream,
                folderSize,
                null
        );
    }

    public static StorageSummary createStorageSummary(int countOfObjects, String path, LocalDateTime localDateTime) {
        return StorageSummary.builder()
                .countOfObjects(countOfObjects)
                .currentPath(path)
                .creationDate(localDateTime)
                .build();
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

    public static DeleteObject createDeleteObject(Item item) {
        return new DeleteObject(item.objectName());
    }

    public static String getFolderPath(Principal principal, FolderRemoveRequest folderRemoveRequest) {
        String path = getUserMainFolder(principal, folderRemoveRequest.getPath());
        String rootFolderForDelete = path.substring(0, path.length() - 1);
        return rootFolderForDelete.substring(0, rootFolderForDelete.lastIndexOf('/') + 1);
    }

    public static String getFilePath(Principal principal, FileRequest fileRequest) {
        String path = getUserMainFolder(principal, fileRequest.getPath());
        return path.substring(0, path.length() - 1);
    }

}