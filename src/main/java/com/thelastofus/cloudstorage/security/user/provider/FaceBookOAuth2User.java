package com.thelastofus.cloudstorage.security.user.provider;

import com.thelastofus.cloudstorage.security.user.CustomOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class FaceBookOAuth2User extends CustomOAuth2User {
    public FaceBookOAuth2User(OAuth2User oAuth2User) {
        super(oAuth2User, "name");
    }
}
