package com.thelastofus.cloudstorage.security.config;

import com.thelastofus.cloudstorage.security.handler.OAuth2LoginSuccessHandler;
import com.thelastofus.cloudstorage.security.service.CustomOAuth2Service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SecurityConfiguration  {

    CustomOAuth2Service customOAuth2Service;
    OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login","/error","/auth/registration","/static/**").permitAll()
                .anyRequest().authenticated())
       .formLogin(login -> login
               .loginPage("/auth/login")
               .loginProcessingUrl("/auth/login")
               .defaultSuccessUrl("/",true)
               .failureUrl("/auth/login?error=true"))
       .logout(logout -> logout
               .logoutUrl("/auth/logout")
               .logoutSuccessUrl("/auth/login"))
       .oauth2Login(oauth2 -> oauth2
               .loginPage("/auth/login")
               .userInfoEndpoint(userEndpoint -> userEndpoint
                       .userService(customOAuth2Service))
               .defaultSuccessUrl("/",true)
               .successHandler(oAuth2LoginSuccessHandler)
               .failureUrl("/auth/login?error=true")
       );

        return http.build();
    }
}
