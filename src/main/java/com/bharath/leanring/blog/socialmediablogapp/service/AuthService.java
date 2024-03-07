package com.bharath.leanring.blog.socialmediablogapp.service;

import com.bharath.leanring.blog.socialmediablogapp.dto.LoginDto;
import com.bharath.leanring.blog.socialmediablogapp.dto.RegisterDto;

public interface AuthService {


    //login
    String login(LoginDto loginDto);

    //register
    String register(RegisterDto registerDto);

}
