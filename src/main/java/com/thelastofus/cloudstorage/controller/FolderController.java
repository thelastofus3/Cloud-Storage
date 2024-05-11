package com.thelastofus.cloudstorage.controller;


import com.thelastofus.cloudstorage.service.FolderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderController {

    private static final String FOLDER_UPLOAD = "/folder/upload";

    FolderService folderService;
}
