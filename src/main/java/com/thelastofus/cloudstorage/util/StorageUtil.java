package com.thelastofus.cloudstorage.util;

import com.thelastofus.cloudstorage.dto.StorageObject;
import com.thelastofus.cloudstorage.dto.StorageSummary;
import io.minio.messages.Item;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;

import static com.thelastofus.cloudstorage.util.TimeUtil.getTimePattern;

@UtilityClass
public class StorageUtil {

    private static final int GMT2_TIME = 2;

    private static final BigDecimal BToKiB_DIVIDER = new BigDecimal("1024");

    public static String getUserFolder(Principal principal, String currentPath) {

        String userFolder = "user-" + principal.getName() + "-files/" ;
        if (!currentPath.isEmpty()) {
            userFolder = userFolder + currentPath + "/";
        }
        return userFolder;
    }

    public static StorageObject createStorageObject(Item item, String userFolder) {
        String objectName = item.objectName();
        String relativePath = cutPath(objectName.substring(userFolder.length())) ;
        String size = String.valueOf(convertFromBToKiB(item));
        String lastModified = item.isDir() ? null : item.lastModified().plusHours(GMT2_TIME).format(getTimePattern());

        return StorageObject.builder()
                .path(relativePath)
                .size(size)
                .lastModified(lastModified)
                .build();
    }

    public static StorageSummary createStorageSummary(int countOfObjects) {
        return StorageSummary.builder()
                .countOfObjects(countOfObjects)
//                .creationDate()
                .build();
    }
    private BigDecimal convertFromBToKiB(Item item) {
        BigDecimal sizeInB = new BigDecimal(item.size());
        return sizeInB.divide(BToKiB_DIVIDER,1, RoundingMode.HALF_UP);
    }

    private String cutPath(String path) {
        int index = path.lastIndexOf('/');
        return index != -1 ? path.substring(0,index) : path;
    }

}
