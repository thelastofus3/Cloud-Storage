package com.thelastofus.cloudstorage.util;

import com.thelastofus.cloudstorage.dto.StorageObject;
import com.thelastofus.cloudstorage.dto.StorageSummary;
import io.minio.messages.Item;
import lombok.experimental.UtilityClass;

import java.security.Principal;

import static com.thelastofus.cloudstorage.util.TimeUtil.getTimePattern;

@UtilityClass
public class StorageUtil {

    private static final int GMT2_TIME = 2;

    public static String getUserParentFolder(Principal principal) {
        return "user-" + principal.getName() + "-files/" ;
    }

    public static StorageObject createStorageObject(Item item, String userFolder) {
        String objectName = item.objectName();
        String relativePath = objectName.substring(userFolder.length());
        String size = String.valueOf(item.size());
        String lastModified = item.isDir() ? null : item.lastModified().plusHours(GMT2_TIME).format(getTimePattern());

        return StorageObject.builder()
                .path(relativePath)
                .size(size)
                .lastModified(lastModified)
                .build();
    }

//    public static StorageSummary createStorageSummary(int countOfObjects) {
//        return StorageSummary.builder()
//                .countOfObjects(countOfObjects)
//                .creationDate()
//                .build();
//    }
}
