package com.example.auth_server.domain.comment.controller;

import com.example.auth_server.domain.comment.dto.CommentResponse;
import com.example.auth_server.domain.comment.dto.CreateCommentRequest;
import com.example.auth_server.domain.comment.dto.UpdateCommentRequest;
import com.example.auth_server.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> createComment (
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest dto
            ) {
        return ResponseEntity.ok(commentService.createComment(postId, dto));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments (@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.findAllComments(postId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment (
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest dto
            ) {
        return ResponseEntity.ok(commentService.updateComment(commentId, dto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
