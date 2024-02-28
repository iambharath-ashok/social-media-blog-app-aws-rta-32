package com.bharath.leanring.blog.socialmediablogapp.service;

import com.bharath.leanring.blog.socialmediablogapp.dto.CommentDto;
import com.bharath.leanring.blog.socialmediablogapp.dto.PatchDto;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(long postId);
    CommentDto getCommentByPostIdAndCommentId(long postId,long id);

    CommentDto updateCommentByPostIdAndCommentId(long postId, long id, CommentDto commentDto);

    CommentDto updateCommentPartiallyByPostIdAndCommentId(Long postId, Long id, PatchDto patchDto);

    CommentDto updateCommentPartiallyByPostIdAndCommentIdUsingJsonPatch(Long postId, Long id, JsonPatch jsonPatch);
}
