package com.thelastofus.cloudstorage.mapper;

import com.thelastofus.cloudstorage.dto.UserRegistrationDto;
import com.thelastofus.cloudstorage.model.User;
import com.thelastofus.cloudstorage.security.user.CustomUserDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegistrationDto convertToDto(User user);

    User convertToUser(UserRegistrationDto userRegistrationDto);

    CustomUserDetails convertToUserDetails(User user);
}
