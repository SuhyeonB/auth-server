package com.example.auth_server.domain.comment.service;


import com.example.auth_server.domain.comment.dto.CommentResponse;
import com.example.auth_server.domain.comment.dto.CreateCommentRequest;
import com.example.auth_server.domain.comment.dto.UpdateCommentRequest;
import com.example.auth_server.domain.comment.entity.Comment;
import com.example.auth_server.domain.comment.repository.CommentRepository;
import com.example.auth_server.domain.post.entity.Post;
import com.example.auth_server.domain.post.repository.PostRepository;
import com.example.auth_server.domain.user.entity.User;
import com.example.auth_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse createComment(Long postId, CreateCommentRequest dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found: " + postId));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + dto.getUserId()));

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(dto.getContent())
                .build();

        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findAllComments(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, UpdateCommentRequest dto) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("comment not found: " + commentId));

        comment.update(dto.getContent());

        return  CommentResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}