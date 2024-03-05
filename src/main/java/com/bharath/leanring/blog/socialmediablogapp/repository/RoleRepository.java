package com.bharath.leanring.blog.socialmediablogapp.repository;

import com.bharath.leanring.blog.socialmediablogapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
