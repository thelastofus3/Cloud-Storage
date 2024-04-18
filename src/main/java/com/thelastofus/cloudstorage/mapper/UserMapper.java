package com.thelastofus.cloudstorage.mapper;

import com.thelastofus.cloudstorage.dto.UserDto;
import com.thelastofus.cloudstorage.model.User;
import com.thelastofus.cloudstorage.security.user.CustomUserDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto convertToDto(User user);

    User convertToUser(UserDto dto);

    CustomUserDetails convertToUserDetails(User user);
}
