package com.example.auth_server.domain.like.controller;

import com.example.auth_server.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like/{postId}")
    public ResponseEntity<Void> likePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId
    ) {
        likeService.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlike/{postId}")
    public ResponseEntity<Void> unlikePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId
    ) {
        likeService.unlikePost(postId, userId);
        return ResponseEntity.ok().build();
    }
}
