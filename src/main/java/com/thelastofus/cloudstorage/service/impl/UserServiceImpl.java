package com.thelastofus.cloudstorage.service.impl;

import com.thelastofus.cloudstorage.dto.user.UserRegistration;
import com.thelastofus.cloudstorage.exception.DateTimeValueNotFoundException;
import com.thelastofus.cloudstorage.exception.UserAlreadyExistException;
import com.thelastofus.cloudstorage.mapper.UserMapper;
import com.thelastofus.cloudstorage.model.Role;
import com.thelastofus.cloudstorage.model.User;
import com.thelastofus.cloudstorage.repository.UserRepository;
import com.thelastofus.cloudstorage.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    @Override
    @Transactional
    public void create(UserRegistration userRegistration) {
        User user = userMapper.convertToUser(userRegistration);

        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UserAlreadyExistException("User %s already exist".formatted(user.getUsername()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setCreated_at(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public LocalDateTime getCreatedAt(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getCreated_at)
                .orElseThrow(() -> new DateTimeValueNotFoundException("Date time value not found for %s".formatted(username)));
    }

}
