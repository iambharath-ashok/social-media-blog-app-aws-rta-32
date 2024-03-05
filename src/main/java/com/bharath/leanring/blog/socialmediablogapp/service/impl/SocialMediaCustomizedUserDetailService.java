package com.bharath.leanring.blog.socialmediablogapp.service.impl;

import com.bharath.leanring.blog.socialmediablogapp.entity.User;
import com.bharath.leanring.blog.socialmediablogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SocialMediaCustomizedUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(()  -> new UsernameNotFoundException("User Not Found with Email or Username:: "+ usernameOrEmail));

        Set<GrantedAuthority> grantedAuthoritySet = userEntity
                .getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), grantedAuthoritySet);
    }
}
