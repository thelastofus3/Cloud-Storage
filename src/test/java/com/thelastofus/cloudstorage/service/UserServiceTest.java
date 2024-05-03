package com.thelastofus.cloudstorage.service;

import com.thelastofus.cloudstorage.dto.UserRegistrationDto;
import com.thelastofus.cloudstorage.exception.UserAlreadyExistException;
import com.thelastofus.cloudstorage.mapper.UserMapper;
import com.thelastofus.cloudstorage.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import lombok.experimental.FieldDefaults;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>(
            "mysql:latest"
    );

    @BeforeEach
    void resetData() {
        userRepository.deleteAll();
    }

    @DynamicPropertySource
    static void registerMysqlProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.generate-ddl",() -> true);
    }

    @Test
    void successSaveInDatabase() {
        var userDto = UserRegistrationDto.builder()
                .username("user")
                .email("user@gmail.com")
                .password("pass")
                .matchingPassword("pass").build();

        userService.create(userDto);

        assertEquals(1,userRepository.findAll().size(),"User success save in database");
    }

    @Test
    void failSaveInDatabaseUserAlreadyExist() {
        var userDto = UserRegistrationDto.builder()
                .username("user")
                .email("user@gmail.com")
                .password("pass")
                .matchingPassword("pass").build();

        userService.create(userDto);

        assertThrows(UserAlreadyExistException.class, () -> userService.create(userDto));

    }
}