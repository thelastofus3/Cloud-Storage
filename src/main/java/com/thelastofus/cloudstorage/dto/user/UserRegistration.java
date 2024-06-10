package com.thelastofus.cloudstorage.dto.user;

import com.thelastofus.cloudstorage.util.password.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@ToString
@PasswordMatches
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegistration {

    @NotBlank(message = "Username should not be empty")
    @Size(min = 4, max = 120, message = "Username should be between 4 and 120 characters")
    String username;
    @NotBlank(message = "Email should not be empty")
    @Email
    String email;
    @NotBlank(message = "Password should not be empty")
    @Size(min = 4, max = 120, message = "Password should be between 4 and 120 characters")
    String password;
    @NotBlank(message = "Matching password should not be empty")
    @Size(min = 4, max = 120, message = "Matching password should be between 4 and 120 characters")
    String matchingPassword;

}
