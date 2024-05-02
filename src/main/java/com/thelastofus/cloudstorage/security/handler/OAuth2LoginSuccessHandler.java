package com.thelastofus.cloudstorage.security.handler;

import com.thelastofus.cloudstorage.exception.UserAlreadyExistException;
import com.thelastofus.cloudstorage.model.Role;
import com.thelastofus.cloudstorage.model.User;
import com.thelastofus.cloudstorage.repository.UserRepository;
import com.thelastofus.cloudstorage.security.user.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    private static final String DEFAULT_PASSWORD = "password";
    private static final String POSTFIX_GMAIL = "@gmail.com";

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String userEmail = getUserEmail(oAuth2User);

        userRepository.findByEmail(userEmail).orElseGet(() -> {
            User user = User.builder()
                    .username(oAuth2User.getName())
                    .email(getUserEmail(oAuth2User))
                    .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                    .role(Role.ROLE_USER)
                    .build();

            return userRepository.save(user);
        });

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private String getUserEmail(CustomOAuth2User oAuth2User) {
        return oAuth2User.getEmail() != null ? oAuth2User.getEmail() : oAuth2User.getName() + POSTFIX_GMAIL;
    }
}
