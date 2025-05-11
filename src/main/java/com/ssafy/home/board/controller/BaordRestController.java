package com.ssafy.home.board.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.NotWrittenMember;
import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.service.BoardService;
import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.member.model.dto.MemberDTO;
import com.ssafy.home.security.dto.CustomUserDetails;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Slf4j
public class BaordRestController implements RestControllerHelper {

	private final BoardService boardService;
	
	@PostMapping("/board-write")
	public ResponseEntity<?> boardAdd(@RequestBody BoardDTO boardDto) {
		try {
			System.out.println(boardDto);
			boardDto.setRegTime(LocalDateTime.now());
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
	
	// 업데이트 필요
//	@PostMapping("/board-update/${boardId}")
//	public ResponseEntity<?> boardModify(@PathVariable int boardId, @AuthenticationPrincipal CustomUserDetails userDetails){ 
//		try {
//			BoardDTO board = boardService.findDetailBoard(boardId);
//			if(board.getUserId()==userDetails.getMember().getId()) {
//				return handleSuccess(Map.of("redirect","/board/board-update-page","board",board));
//			} else throw new NotWrittenMember();
//		}catch (RuntimeException e) {
//			e.printStackTrace();
//			return handleFail(e);
//		}
//	}
	
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
			return handleSuccess(Map.of("redirect","/board/board-detail","board",board));
		}catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
		
	}
	
}
