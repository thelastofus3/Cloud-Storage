package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.user.UserRegistration;
import com.thelastofus.cloudstorage.model.User;

public interface UserService {

    User getByUsername(String username);

    void create(UserRegistration userRegistration);
}
