package com.bharath.leanring.blog.socialmediablogapp.service;

import com.bharath.leanring.blog.socialmediablogapp.dto.PostDto;
import com.bharath.leanring.blog.socialmediablogapp.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
