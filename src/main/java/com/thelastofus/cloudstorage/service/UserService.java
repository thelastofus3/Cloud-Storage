package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.user.UserRegistration;
import com.thelastofus.cloudstorage.model.User;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public interface UserService {

    void create(UserRegistration userRegistration);

    LocalDateTime getCreatedAt(String username);
}
