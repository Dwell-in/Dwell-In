package com.ssafy.home.board.model.service;

import java.util.List;

import com.ssafy.home.board.model.dto.CommentDTO;

public interface CommentService {
	
	int addComment(CommentDTO comment);
	
	List<CommentDTO> findComment(int boardId);

	int modifyComment(CommentDTO comment);
	
	int removeComment(int CommentId);
	
}
