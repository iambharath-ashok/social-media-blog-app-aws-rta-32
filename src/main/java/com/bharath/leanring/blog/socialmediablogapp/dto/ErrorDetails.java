package com.bharath.leanring.blog.socialmediablogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class ErrorDetails {

    private LocalDateTime timeStamp;
    private String message;
    private String details;
}
