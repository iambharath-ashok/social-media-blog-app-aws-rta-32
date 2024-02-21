package com.bharath.leanring.blog.socialmediablogapp.repository;

import com.bharath.leanring.blog.socialmediablogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
