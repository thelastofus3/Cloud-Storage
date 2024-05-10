package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.repository.FileRepository;
import com.thelastofus.cloudstorage.repository.FolderRepository;
import com.thelastofus.cloudstorage.service.FolderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static com.thelastofus.cloudstorage.util.MinioUtil.getUserParentFolder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderServiceImpl implements FolderService {

}
