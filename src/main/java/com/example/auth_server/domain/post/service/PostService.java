package com.example.auth_server.domain.post.service;

import com.example.auth_server.domain.comment.repository.CommentRepository;
import com.example.auth_server.domain.like.repository.LikeRepository;
import com.example.auth_server.domain.post.dto.CreatePostRequest;
import com.example.auth_server.domain.post.dto.PostResponse;
import com.example.auth_server.domain.post.dto.UpdatePostRequest;
import com.example.auth_server.domain.post.entity.Post;
import com.example.auth_server.domain.post.repository.PostRepository;
import com.example.auth_server.domain.user.entity.User;
import com.example.auth_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public PostResponse createPost(CreatePostRequest dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + dto.getUserId()));

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();

        postRepository.save(post);

        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public PostResponse findPostById(Long postId) {
        return postRepository.findById(postId)
                .map(PostResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("post not found: " + postId));
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponse::from);
    }

    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found: " + postId));

        post.update(dto.getTitle(), dto.getContent());

        return PostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("post not found: " + postId);
        }

        commentRepository.deleteByPostId(postId);
        likeRepository.deleteByPostId(postId);
        postRepository.deleteById(postId);
    }
}
