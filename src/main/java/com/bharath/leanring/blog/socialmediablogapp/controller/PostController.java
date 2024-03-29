package com.bharath.leanring.blog.socialmediablogapp.controller;

import com.bharath.leanring.blog.socialmediablogapp.dto.PostDto;
import com.bharath.leanring.blog.socialmediablogapp.dto.PostDtoV2;
import com.bharath.leanring.blog.socialmediablogapp.payload.PostResponse;
import com.bharath.leanring.blog.socialmediablogapp.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(
        name = "SOCIAL MEDIAL POST RESOURCE CRUD REST APIs"
)
public class PostController {

    @Autowired
    private PostService postService;

    //POST /api/posts
    @PostMapping("/v1/posts")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Create Post REST API",
            description = "Create REST API is used to create new Posts on Social Media Blog Application"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto) {
        PostDto savedPostDto = postService.createPost(postDto);
        return new ResponseEntity(savedPostDto, HttpStatus.CREATED);
    }

    //GET /api/posts
    // Pagination and Sorting
    @GetMapping("/v1/posts")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get All Posts REST API",
            description = "Get All Posts REST API is used to fetch all the posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public PostResponse getAllPosts(
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = "id", required = false) String sortDir

    ) {
       return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //GET /api/posts/{id}
    @GetMapping(value = "/posts/{id}", produces  = "application/vnd.socialmediablog.v1+json")
    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API is used to fetch single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
       return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping(value = "/posts/{id}", produces  = "application/vnd.socialmediablog.v2+json")
    @Operation(
            summary = "Get Post By Id REST API v2",
            description = "Get Post By Id v2 REST API is used to fetch single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable long id) {
        PostDto postDtoV1 = postService.getPostById(id);
        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setId(postDtoV1.getId());
        postDtoV2.setTitle(postDtoV1.getTitle());
        postDtoV2.setContent(postDtoV1.getContent());
        postDtoV2.setDescription(postDtoV1.getDescription());
        List<String> tags = new ArrayList<>();
        tags.add("Java 21");
        tags.add("Spring Boot");
        tags.add("Microservices");

        postDtoV2.setTags(tags);

        return ResponseEntity.ok(postDtoV2);
    }

    //PUT /api/posts/{id}

    @PutMapping("/v1/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bear Authentication")
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API is used update a particular post in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<PostDto> updatePost(@RequestBody @Valid PostDto postDto, @PathVariable long id) {
       PostDto updatedPostResponse = postService.updatePost(postDto, id);
       return ResponseEntity.ok(updatedPostResponse);
    }

    //DELETE ///api/posts/{id}
    @DeleteMapping("/v1/posts/{id}")
    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used delete a particular post in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok("Deleted Successfully Post Resource :: "+id);
    }


}
