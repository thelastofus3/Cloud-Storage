package com.thelastofus.cloudstorage.mapper;

import com.thelastofus.cloudstorage.dto.user.UserRegistration;
import com.thelastofus.cloudstorage.model.User;
import com.thelastofus.cloudstorage.security.user.CustomUserDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegistration convertToDto(User user);

    User convertToUser(UserRegistration userRegistration);

    CustomUserDetails convertToUserDetails(User user);
}
