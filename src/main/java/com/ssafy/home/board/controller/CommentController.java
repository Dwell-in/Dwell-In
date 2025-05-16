package com.ssafy.home.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.model.dto.CommentDTO;
import com.ssafy.home.board.model.service.CommentService;
import com.ssafy.home.common.RestControllerHelper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController implements RestControllerHelper {

    private final CommentService cService;

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDTO comment) {
        try {
            int result = cService.addComment(comment);
            return handleSuccess(result);
        } catch (Exception e) {
            return handleFail(e);
        }
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getComments(@PathVariable int boardId) {
        try {
            List<CommentDTO> comments = cService.findComment(boardId);
            return handleSuccess(comments);
        } catch (Exception e) {
            return handleFail(e);
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable int commentId, @RequestBody CommentDTO comment) {
        try {
            comment.setCommentId(commentId);
            int result = cService.modifyComment(comment);
            return handleSuccess(result);
        } catch (Exception e) {
            return handleFail(e);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId) {
        try {
            int result = cService.removeComment(commentId);
            return handleSuccess(result);
        } catch (Exception e) {
            return handleFail(e);
        }
    }
}