package com.capstone.moa.controller;

import com.capstone.moa.dto.WriteCommentRequest;
import com.capstone.moa.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> writeComment(@PathVariable("postId") Long postId, @RequestBody WriteCommentRequest request) {
        commentService.writeComment(postId, request);
        return ResponseEntity.noContent().build();
    }
}