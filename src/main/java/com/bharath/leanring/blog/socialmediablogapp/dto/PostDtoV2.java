package com.bharath.leanring.blog.socialmediablogapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(
        description = "PostDtoV2 Model Information"
)
public class PostDtoV2 {
    private Long id;

    // min 5 chars is required
    // Should not be null
    @Schema(description = "Post title V2")
    @NotEmpty
    @Size(min = 5, message = "Post title should have at least 5 characters", max = 100)
    private String title;

    @Schema(description = "Blog Post Description V2")
    @Size(min = 10, message = "Post Description should have at least 10 character")
    private String description;

    @Schema(description = "Blog Post Content V2")
    @NotEmpty
    private String content;

    @Schema(description = "Blog Post Tags")
    private List<String> tags = new ArrayList<>();
}
