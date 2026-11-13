package com.example.auth_server.domain.like.service;

import com.example.auth_server.domain.like.entity.PostLike;
import com.example.auth_server.domain.like.repository.LikeRepository;
import com.example.auth_server.domain.post.entity.Post;
import com.example.auth_server.domain.post.repository.PostRepository;
import com.example.auth_server.domain.user.entity.User;
import com.example.auth_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void likePost(Long postId, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found: " + postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + userId));

        if (likeRepository.existsByUserIdAndPostId(userId, postId)) return;

        PostLike like  = PostLike.builder()
                .post(post)
                .user(user)
                .build();

        likeRepository.save(like);
    }

    @Transactional
    public void unlikePost(Long postId, Long userId) {
        likeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
