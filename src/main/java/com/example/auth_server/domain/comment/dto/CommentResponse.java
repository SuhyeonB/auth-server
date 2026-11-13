package com.example.auth_server.domain.comment.dto;

import com.example.auth_server.domain.comment.entity.Comment;
import lombok.Builder;

@Builder
public record CommentResponse(Long id, String writer, String content) {

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .writer(comment.getUser().getNickname())
                .content(comment.getContent())
                .build();
    }
}
