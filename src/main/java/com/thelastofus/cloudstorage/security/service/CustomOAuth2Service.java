package com.thelastofus.cloudstorage.security.service;

import com.thelastofus.cloudstorage.exception.UnsupportedRegistrationServiceException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    Map<String, Function<OAuth2User, OAuth2User>> oauthUserFactories;

    @Override
    @SneakyThrows
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        return oauthUserFactories.getOrDefault(registrationId, user -> {
            throw new UnsupportedRegistrationServiceException("Unsupported registrationId: %s".formatted(registrationId));
        }).apply(oAuth2User);
    }
}
