package com.thelastofus.cloudstorage.security;

import com.thelastofus.cloudstorage.security.UserDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {


    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
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
               .logoutSuccessUrl("/auth/login"));

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//
//        provider.setPasswordEncoder(getPasswordEncoder());
//        provider.setUserDetailsService(userDetailsService);
//
//        return provider;
//    }

}
