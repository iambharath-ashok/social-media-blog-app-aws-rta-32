package com.bharath.leanring.blog.socialmediablogapp.service;

import com.bharath.leanring.blog.socialmediablogapp.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(long postId);



}
