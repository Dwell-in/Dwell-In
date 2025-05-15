package com.ssafy.home.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.board.model.dto.CommentDTO;

@Mapper
public interface CommentDAO {
	
	int insertComment(CommentDTO comment);
	
	List<CommentDTO> selectComment(int boardId);

	int updateComment(CommentDTO comment);
	
	int deleteComment(int CommentId);
	
}
