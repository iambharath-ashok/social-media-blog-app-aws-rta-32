package com.bharath.leanring.blog.socialmediablogapp.controller;

import com.bharath.leanring.blog.socialmediablogapp.dto.CommentDto;
import com.bharath.leanring.blog.socialmediablogapp.dto.PatchDto;
import com.bharath.leanring.blog.socialmediablogapp.entity.Comment;
import com.bharath.leanring.blog.socialmediablogapp.service.CommentService;
import com.github.fge.jsonpatch.JsonPatch;
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
        return new ResponseEntity<>(savedCommentDto, HttpStatus.CREATED);
    }


    //Get All Comments -  /api/v1/posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> fetchAllCommentsByPostId(@PathVariable("postId") Long postId) {
        List<CommentDto> commentDtoList = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }


    //GET Comment By CommentId and PostId - /api/v1/posts/{postId}/comments/{id}
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> fetchCommentByPostIdAndCommentId(@PathVariable("postId") Long postId, @PathVariable("id") Long id) {
        CommentDto commentDto = commentService.getCommentByPostIdAndCommentId(postId, id);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    // PUT Update Comment By PostId and CommentId /api/v1/posts/{postId}/comments/{id}
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentByPostIdAndCommentId(@PathVariable("postId") Long postId,
                                                                        @PathVariable("id") Long id,
                                                                        @RequestBody CommentDto commentDto) {
        CommentDto updatedCommentDto = commentService.updateCommentByPostIdAndCommentId(postId, id, commentDto);
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }


    //Delete Comment By CommentId and PostId - /api/v1/posts/{postId}/comments/{id}


    //Patch Comment By CommentId and PostId
   // @PatchMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> partiallyUpdateCommentByPostIdAndCommentId(@PathVariable("postId") Long postId,
                                                                                 @PathVariable("id") Long id,
                                                                                 @RequestBody PatchDto patchDto) {
        CommentDto updatedCommentDto = null;
        if (patchDto.getOperation().equalsIgnoreCase("update")) {
            updatedCommentDto = commentService.updateCommentPartiallyByPostIdAndCommentId(postId, id, patchDto);
        } else if (patchDto.getOperation().equalsIgnoreCase("delete")) {
            //updatedCommentDto =  commentService.deleteParticularField(postId, id, patchDto);
        }
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }

    @PatchMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> partiallyUpdateCommentByPostIdAndCommentIdUsingJsonPatchLib(@PathVariable("postId") Long postId,
                                                                                                  @PathVariable("id") Long id,
                                                                                                  @RequestBody JsonPatch jsonPatch) {
        CommentDto updatedCommentDto = null;
        updatedCommentDto = commentService.updateCommentPartiallyByPostIdAndCommentIdUsingJsonPatch(postId, id, jsonPatch);
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }


}
