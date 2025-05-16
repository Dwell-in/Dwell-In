package com.ssafy.home.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.board.model.dao.CommentDAO;
import com.ssafy.home.board.model.dto.CommentDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;

    @Override
    public int addComment(CommentDTO comment) {
        return commentDAO.insertComment(comment);
    }

    @Override
    public List<CommentDTO> findComment(int boardId) {
        return commentDAO.selectComment(boardId);
    }

    @Override
    public int modifyComment(CommentDTO comment) {
        return commentDAO.updateComment(comment);
    }

    @Override
    public int removeComment(int commentId) {
        return commentDAO.deleteComment(commentId);
    }
}