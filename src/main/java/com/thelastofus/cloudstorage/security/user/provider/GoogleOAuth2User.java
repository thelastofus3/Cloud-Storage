package com.thelastofus.cloudstorage.security.user.provider;

import com.thelastofus.cloudstorage.security.user.CustomOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleOAuth2User extends CustomOAuth2User {
    public GoogleOAuth2User(OAuth2User oAuth2User) {
        super(oAuth2User, "name");
    }
}
