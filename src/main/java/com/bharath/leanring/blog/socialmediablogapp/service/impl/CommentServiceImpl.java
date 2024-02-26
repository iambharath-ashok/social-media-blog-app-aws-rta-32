package com.bharath.leanring.blog.socialmediablogapp.service.impl;

import com.bharath.leanring.blog.socialmediablogapp.dto.CommentDto;
import com.bharath.leanring.blog.socialmediablogapp.entity.Comment;
import com.bharath.leanring.blog.socialmediablogapp.entity.Post;
import com.bharath.leanring.blog.socialmediablogapp.exception.ResourceNotFoundException;
import com.bharath.leanring.blog.socialmediablogapp.repository.CommentRepository;
import com.bharath.leanring.blog.socialmediablogapp.repository.PostRepository;
import com.bharath.leanring.blog.socialmediablogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {


        // Map Comment DTO to Comment Entity
        Comment comment = mapDtoToEntity(commentDto);
        // Fetch Post from Post Repository using PostId from Request URI
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        // Set Comment to Post
        comment.setPost(post);
        // Save Comment To DB
        Comment savedCommentEntity = commentRepository.save(comment);
        // Map Comment Entity to Comment DTO
        CommentDto saveCommentDto = mapEntityToDto(savedCommentEntity);

        return saveCommentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        //Map Entity to Dto
        List<CommentDto> commentDtoList = comments.stream().map(comment -> mapEntityToDto(comment)).collect(Collectors.toList());
        return commentDtoList;
    }

    private CommentDto mapEntityToDto(Comment savedCommentEntity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(savedCommentEntity.getId());
        commentDto.setName(savedCommentEntity.getName());
        commentDto.setBody(savedCommentEntity.getBody());
        commentDto.setEmail(savedCommentEntity.getEmail());
        return commentDto;
    }

    private Comment mapDtoToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
