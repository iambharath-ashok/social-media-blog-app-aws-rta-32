package com.bharath.leanring.blog.socialmediablogapp.repository;

import com.bharath.leanring.blog.socialmediablogapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);
}
