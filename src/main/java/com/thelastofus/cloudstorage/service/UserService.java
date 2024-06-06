package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.user.UserRegistration;

import java.time.LocalDateTime;

public interface UserService {

    void create(UserRegistration userRegistration);

    LocalDateTime getCreatedAt(String username);
}
