package com.bharath.leanring.blog.socialmediablogapp.service.impl;

import com.bharath.leanring.blog.socialmediablogapp.dto.CommentDto;
import com.bharath.leanring.blog.socialmediablogapp.dto.PatchDto;
import com.bharath.leanring.blog.socialmediablogapp.entity.Comment;
import com.bharath.leanring.blog.socialmediablogapp.entity.Post;
import com.bharath.leanring.blog.socialmediablogapp.exception.BlogAPIException;
import com.bharath.leanring.blog.socialmediablogapp.exception.ResourceNotFoundException;
import com.bharath.leanring.blog.socialmediablogapp.repository.CommentRepository;
import com.bharath.leanring.blog.socialmediablogapp.repository.PostRepository;
import com.bharath.leanring.blog.socialmediablogapp.service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

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

    @Override
    public CommentDto getCommentByPostIdAndCommentId(long postId, long id) {

        //Fetch Post by PostId
        Post postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        //Fetch Comment by CommentId
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(id)));

        // validate comment belongs to that Particular Post
        if (!commentEntity.getPost().getId().equals(postEntity.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bad Request Comment Not Found in Post");
        }
        // Map Comment Entity to Comment DTO
        CommentDto commentDto = mapEntityToDto(commentEntity);
        return commentDto;
    }

    @Override
    public CommentDto updateCommentByPostIdAndCommentId(long postId, long id, CommentDto commentDto) {
        //Fetch Post by PostId
        Post postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        //Fetch Comment by CommentId
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(id)));

        // validate comment belongs to that Particular Post
        if (!commentEntity.getPost().getId().equals(postEntity.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bad Request Comment Not Found in Post");
        }


        // Update old Comment Details With new Comment Dto
        commentEntity.setName(commentDto.getName());
        commentEntity.setEmail(commentDto.getEmail());
        commentEntity.setBody(commentDto.getBody());

        //Save Updated Comment Entity
        Comment updatedCommentEntity  = commentRepository.save(commentEntity);

        // Map Comment Entity to Comment DTO
        CommentDto updatedCommentDto = mapEntityToDto(updatedCommentEntity);

        return updatedCommentDto;
    }

    @Override
    public CommentDto updateCommentPartiallyByPostIdAndCommentId(Long postId, Long id, PatchDto patchDto) {
        Post postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        //Fetch Comment by CommentId
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(id)));

        // validate comment belongs to that Particular Post
        if (!commentEntity.getPost().getId().equals(postEntity.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bad Request Comment Not Found in Post");
        }


        partiallyUpdateCommentEntity(patchDto, commentEntity);

        Comment updatedCommentEntity  = commentRepository.save(commentEntity);

        // Map Comment Entity to Comment DTO
        CommentDto updatedCommentDto = mapEntityToDto(updatedCommentEntity);

        return updatedCommentDto;
    }

    @Override
    public CommentDto updateCommentPartiallyByPostIdAndCommentIdUsingJsonPatch(Long postId, Long id, JsonPatch jsonPatch) {
        Post postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        //Fetch Comment by CommentId
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(id)));

        // validate comment belongs to that Particular Post
        if (!commentEntity.getPost().getId().equals(postEntity.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bad Request Comment Not Found in Post");
        }

        CommentDto commentDto = mapEntityToDto(commentEntity);

        try {
            commentDto =  applyPatchToComment(jsonPatch, commentDto);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Comment updatedCommentEntity = mapDtoToEntity(commentDto);
        updatedCommentEntity.setId(commentEntity.getId());
        updatedCommentEntity.setPost(postEntity);
        commentRepository.save(updatedCommentEntity);
        return commentDto;
    }


    private CommentDto mapEntityToDto(Comment savedCommentEntity) {
        return  modelMapper.map(savedCommentEntity, CommentDto.class);
    }

    private Comment mapDtoToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    private void partiallyUpdateCommentEntity(PatchDto patchDto, Comment commentEntity) {

        String key = patchDto.getKey();
        switch (patchDto.getKey()) {
            case  "Email" :
                commentEntity.setEmail(patchDto.getValue());
                break;
            case "Body" :
                commentEntity.setBody(patchDto.getValue());
                break;
            case "Name" :
                commentEntity.setName(patchDto.getValue());
                break;
        }
    }

    private CommentDto applyPatchToComment(
            JsonPatch patch, CommentDto commentDto) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(commentDto, JsonNode.class));
        return objectMapper.treeToValue(patched, CommentDto.class);
    }

}
