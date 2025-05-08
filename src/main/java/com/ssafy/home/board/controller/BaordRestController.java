package com.ssafy.home.board.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.NotWrittenMember;
import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.service.BoardService;
import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.member.model.dto.MemberDTO;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Slf4j
public class BaordRestController implements RestControllerHelper {

	private final BoardService boardService;
	
	@PostMapping("/board-write")
	public ResponseEntity<?> boardAdd(@ModelAttribute BoardDTO boardDto) {
		try {
		boardDto.setRegDate(LocalDateTime.now());
		boardService.addBoard(boardDto);
		return handleSuccess(boardDto, HttpStatus.CREATED);
		}catch(RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@PostMapping("/board-delete")
	public ResponseEntity<?> boardRemove(@RequestParam int boardId) {
		try {
		boardService.removeBoard(boardId);
		return handleSuccess("삭제 성공", HttpStatus.OK);
		}catch(RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@PostMapping("/board-update")
	public ResponseEntity<?> boardModify(@ModelAttribute BoardDTO boardDTO, HttpSession session){
		try {
			MemberDTO member = (MemberDTO)session.getAttribute("loginUser");
			if(member.getEmail().equals(boardDTO.getEmail())) {
			boardService.modifyBoard(boardDTO);
			return handleSuccess(boardDTO,HttpStatus.OK);
			}else {
				throw new NotWrittenMember();
			}
		}catch(RuntimeException e) {
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
			return handleSuccess(board,HttpStatus.OK);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
		
	}
	
}
