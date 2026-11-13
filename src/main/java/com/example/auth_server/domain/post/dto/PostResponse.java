package com.example.auth_server.domain.post.dto;

import com.example.auth_server.domain.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse (
        Long id,
        String title,
        String content,
        String writer,
        LocalDateTime createdAt){

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
