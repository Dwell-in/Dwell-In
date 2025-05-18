package com.ssafy.home.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.dto.PostSearchCondition;

@Mapper
public interface BoardDAO {

	List<BoardDTO> searchPosts(PostSearchCondition condition);

	BoardDTO selectDetailBoard(int boardId);
	
	int updateViewCount(int boardId);

	int insertBoard(BoardDTO boardDto);

	int deleteBoard(int boardId);
	
    int updateBoard(BoardDTO boardDTO);
    
    int countPosts(PostSearchCondition condition);

}
