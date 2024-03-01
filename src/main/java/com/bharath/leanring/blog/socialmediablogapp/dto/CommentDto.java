package com.bharath.leanring.blog.socialmediablogapp.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;

    @Size(min = 3, max = 50, message = "Name should have minimum of 3 and max of 50 characters")
    @NotEmpty(message = "Name should not be null or Empty")
    private String name;

    @Email
    @NotNull(message = "Email should not be null or empty")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum of 10 characters")
    private String body;

}
