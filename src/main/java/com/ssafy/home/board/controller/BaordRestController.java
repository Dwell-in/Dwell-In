package com.ssafy.home.board.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.service.BoardService;
import com.ssafy.home.common.RestControllerHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Slf4j
public class BaordRestController implements RestControllerHelper {

	private final BoardService boardService;
	
	@GetMapping("/post-list")
	public ResponseEntity<?> boardList(Model model) {
		List<BoardDTO> boardList = boardService.findBoard();
		System.out.println(boardList);
		return handleSuccess(Map.of("data", boardList));
	}
	
	@PostMapping("/board-write")
	public ResponseEntity<?> boardAdd(@RequestBody BoardDTO board) {
		try {
			System.out.println(board);
			board.setRegTime(LocalDateTime.now());
			boardService.addBoard(board);
		return handleSuccess(board, HttpStatus.CREATED);
		}catch(RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@PostMapping("/board-delete/{boardId}")
	public ResponseEntity<?> boardRemove(@PathVariable int boardId) {
		try {
		boardService.removeBoard(boardId);
		return handleSuccess("삭제 성공", HttpStatus.OK);
		}catch(RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@PostMapping("/board-update/{boardId}")
	public ResponseEntity<?> boardModify(@PathVariable int boardId, @RequestBody BoardDTO board){ 
		try {
			board.setBoardId(boardId);
			boardService.modifyBoard(board);
			return handleSuccess(board);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@GetMapping("/board-search")
	public ResponseEntity<?> boardSearch(@RequestParam String search){
		try {
		List<BoardDTO> boardList = boardService.findSearchBoard(search);
		return handleSuccess(boardList);
		}catch(RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@GetMapping("/board-detail/{boardId}")
	public ResponseEntity<?> boardDetails(@PathVariable int boardId ) {
		try {
			BoardDTO board = boardService.findDetailBoard(boardId);
			return handleSuccess(Map.of("redirect","/board/post-detail","board",board));
		}catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
		
	}
	
}
