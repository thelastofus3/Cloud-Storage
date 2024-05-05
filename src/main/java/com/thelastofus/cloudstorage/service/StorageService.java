package com.thelastofus.cloudstorage.service;

import java.security.Principal;
import java.util.List;

public interface StorageService {

    List<String> getAllFiles(Principal principal);

}
