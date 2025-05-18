package com.ssafy.home.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.dto.Page;

@Mapper
public interface BoardDao {

	List<BoardDTO> selectBoard();

	BoardDTO selectDetailBoard(int boardId);
	
	int updateViewCount(int boardId);

	int insertBoard(BoardDTO boardDto);

	int deleteBoard(int boardId);
	
    int updateBoard(BoardDTO boardDTO);

	List<BoardDTO> selectSearchBoard(String search);
}
