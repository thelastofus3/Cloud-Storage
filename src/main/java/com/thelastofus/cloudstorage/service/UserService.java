package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.UserRegistration;
import com.thelastofus.cloudstorage.model.User;

public interface UserService {

    User getByUsername(String username);

    void create(UserRegistration userRegistration);
}
