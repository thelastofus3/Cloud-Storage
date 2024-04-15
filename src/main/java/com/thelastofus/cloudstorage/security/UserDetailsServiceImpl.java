package com.thelastofus.cloudstorage.security;

import com.thelastofus.cloudstorage.mapper.UserMapper;
import com.thelastofus.cloudstorage.model.User;
import com.thelastofus.cloudstorage.repository.UserRepository;
import com.thelastofus.cloudstorage.security.CustomUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username)  {
        return userRepository.findByUsername(username)
                .map(userMapper::convertToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));

    }
}
