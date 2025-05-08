package com.ssafy.home.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.board.model.dao.BoardDao;
import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.dto.Page;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardDao bdao;

	public List<BoardDTO> findBoard() {
		// TODO Auto-generated method stub
		return bdao.selectBoard();
	}

	public List<BoardDTO> findSearchBoard(String search) {
		return bdao.selectSearchBoard(search);
	}

	@Transactional
	public BoardDTO findDetailBoard(int boardId) {
		bdao.updateViewCount(boardId);
		return bdao.selectDetailBoard(boardId);
	}

	@Transactional
	public void addBoard(BoardDTO boardDto) {
		// TODO Auto-generated method stub
		bdao.insertBoard(boardDto);
	}

	@Transactional
	public void removeBoard(int boardId) {
		// TODO Auto-generated method stub
		bdao.deleteBoard(boardId);

	}

	@Transactional
	public void modifyBoard(BoardDTO boardDTO) {
		bdao.updateBoard(boardDTO);
	}
}
