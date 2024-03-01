package com.bharath.leanring.blog.socialmediablogapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class PostDto {

    private Long id;

    // min 5 chars is required
    // Should not be null
    @NotEmpty
    @Size(min = 5, message = "Post title should have at least 5 characters", max = 100)
    private String title;

    @Size(min = 10, message = "Post Description should have at least 10 character")
    private String description;

    @NotEmpty
    private String content;

}
