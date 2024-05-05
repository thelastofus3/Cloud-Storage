package com.thelastofus.cloudstorage.util;

import lombok.experimental.UtilityClass;

import java.security.Principal;

@UtilityClass
public class MinioUtil {

    public static String getUserParentFolder(Principal principal) {
        return "user-" + principal.getName() + "-files/" ;
    }

}
