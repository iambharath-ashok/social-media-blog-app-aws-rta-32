package com.bharath.leanring.blog.socialmediablogapp.controller;

import com.bharath.leanring.blog.socialmediablogapp.dto.CommentDto;
import com.bharath.leanring.blog.socialmediablogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {


    @Autowired
    private CommentService commentService;


    // Create new Post - /api/v1/posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId, @RequestBody CommentDto commentDto) {
        CommentDto savedCommentDto = commentService.createComment(postId, commentDto);
        return  new ResponseEntity<>(savedCommentDto, HttpStatus.CREATED);
    }


    //Get All Comments -  /api/v1/posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> fetchAllCommentsByPostId(@PathVariable("postId") Long postId) {
        List<CommentDto> commentDtoList = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }


}
