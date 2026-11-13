package com.example.auth_server.domain.like.repository;

import com.example.auth_server.domain.like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
    void deleteByPostId(Long postId);
}
