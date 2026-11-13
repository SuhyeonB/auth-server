package com.example.auth_server.domain.post.repository;

import com.example.auth_server.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
