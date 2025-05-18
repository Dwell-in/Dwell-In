package com.ssafy.home.board.model.service;

import java.util.List;

import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.dto.PostSearchCondition;

public interface BoardService {
	
	public void addBoard(BoardDTO boardDto);
	
	List<BoardDTO> findPosts(PostSearchCondition condition);
	
	public BoardDTO findDetailBoard(int boardId);

	public void modifyBoard(BoardDTO boardDTO);

	public void removeBoard(int boardId);
	
	int countPosts(PostSearchCondition condition);

}
