package com.example.auth_server.domain.comment.controller;

import com.example.auth_server.domain.comment.dto.CommentResponse;
import com.example.auth_server.domain.comment.dto.CreateCommentRequest;
import com.example.auth_server.domain.comment.dto.UpdateCommentRequest;
import com.example.auth_server.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> createComment (
            @AuthenticationPrincipal Long userId,
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest dto
    ) {
        return ResponseEntity.ok(commentService.createComment(userId, postId, dto));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments (@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.findAllComments(postId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment (
            @AuthenticationPrincipal Long userId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest dto
    ) {
        return ResponseEntity.ok(commentService.updateComment(userId, commentId, dto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(userId, commentId);
        return ResponseEntity.noContent().build();
    }
}
