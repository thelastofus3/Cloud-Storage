package com.thelastofus.cloudstorage.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false,unique = true,length = 120)
    String username;
    @Column(nullable = false,unique = true)
    String email;
    @Column(nullable = false,length = 120)
    String password;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    Role role;
    @Column(nullable = false)
    LocalDateTime created_at;

}
