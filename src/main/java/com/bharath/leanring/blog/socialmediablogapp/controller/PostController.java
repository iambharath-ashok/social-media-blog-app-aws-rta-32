package com.bharath.leanring.blog.socialmediablogapp.controller;

import com.bharath.leanring.blog.socialmediablogapp.dto.PostDto;
import com.bharath.leanring.blog.socialmediablogapp.payload.PostResponse;
import com.bharath.leanring.blog.socialmediablogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/posts")
public class PostController {

    @Autowired
    private PostService postService;

    //POST /api/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto savedPostDto = postService.createPost(postDto);
        return new ResponseEntity(savedPostDto, HttpStatus.CREATED);
    }

    //GET /api/posts
    // Pagination and Sorting
    @GetMapping
    public PostResponse getAllPosts(
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = "id", required = false) String sortDir

    ) {
       return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //GET /api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
       return ResponseEntity.ok(postService.getPostById(id));
    }

    //PUT /api/posts/{id}

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable long id) {
       PostDto updatedPostResponse = postService.updatePost(postDto, id);
       return ResponseEntity.ok(updatedPostResponse);
    }

    //DELETE ///api/posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok("Deleted Successfully Post Resource :: "+id);
    }


}
