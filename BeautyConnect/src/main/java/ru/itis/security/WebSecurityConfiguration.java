package ru.itis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/registration").anonymous()
                        .anyRequest().permitAll())
                .formLogin(formLogin -> formLogin
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .rememberMe(Customizer.withDefaults());

        return httpSecurity.build();
    }

}
