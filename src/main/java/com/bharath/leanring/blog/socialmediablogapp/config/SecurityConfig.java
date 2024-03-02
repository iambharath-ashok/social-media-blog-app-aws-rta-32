package com.bharath.leanring.blog.socialmediablogapp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                .csrf( csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                     //   .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll())
                        .httpBasic(Customizer.withDefaults());


        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails bharath = User.builder()
                .username("bharath")
               .password(passwordEncoder().encode("Guru0421"))
                //.password("R3VydTA0MjE=")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(bharath, admin);

    }



}
