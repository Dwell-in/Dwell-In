package com.ssafy.home.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.board.model.dao.BoardDAO;
import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.dto.PostSearchCondition;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardDAO bdao;

	@Transactional
	public void addBoard(BoardDTO boardDto) {
		bdao.insertBoard(boardDto);
	}

	@Override
	public List<BoardDTO> findPosts(PostSearchCondition condition) {
		return bdao.searchPosts(condition);
	}
	
	@Transactional
	public BoardDTO findDetailBoard(int boardId) {
		bdao.updateViewCount(boardId);
		return bdao.selectDetailBoard(boardId);
	}

	@Transactional
	public void removeBoard(int boardId) {
		bdao.deleteBoard(boardId);

	}

	@Transactional
	public void modifyBoard(BoardDTO boardDTO) {
		bdao.updateBoard(boardDTO);
	}
	
	@Override
	public int countPosts(PostSearchCondition condition) {
	    return bdao.countPosts(condition);
	}

}
