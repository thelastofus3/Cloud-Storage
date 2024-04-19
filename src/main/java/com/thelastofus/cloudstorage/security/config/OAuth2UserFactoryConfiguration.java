package com.thelastofus.cloudstorage.security.config;

import com.thelastofus.cloudstorage.security.user.provider.FaceBookOAuth2User;
import com.thelastofus.cloudstorage.security.user.provider.GitHubOAuth2User;
import com.thelastofus.cloudstorage.security.user.provider.GoogleOAuth2User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class OAuth2UserFactoryConfiguration {
    @Bean
    public Map<String, Function<OAuth2User, OAuth2User>> oauthUserFactories() {
        Map<String, Function<OAuth2User, OAuth2User>> factories = new HashMap<>();
        factories.put("github", GitHubOAuth2User::new);
        factories.put("google", GoogleOAuth2User::new);
        factories.put("facebook", FaceBookOAuth2User::new);
        return factories;
    }
}

