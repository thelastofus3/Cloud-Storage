package com.thelastofus.cloudstorage.repository.impl;

import com.thelastofus.cloudstorage.repository.FolderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FolderRepositoryImpl implements FolderRepository {

}
