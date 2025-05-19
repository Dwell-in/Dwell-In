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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.dto.Page;
import com.ssafy.home.board.model.dto.PostSearchCondition;
import com.ssafy.home.board.model.service.BoardServiceImpl;
import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.security.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Slf4j
public class BaordRestController implements RestControllerHelper {

	private final BoardServiceImpl boardService;
	
	@GetMapping("/post-list")
	public ResponseEntity<?> boardList(@ModelAttribute PostSearchCondition condition) {
	    // 전체 개수 조회
	    int totalCount = boardService.countPosts(condition);

	    // 페이지 정보 생성 및 계산
	    Page page = Page.builder()
	        .page(condition.getPage())
	        .size(condition.getSize())
	        .build();
	    page.calculate(totalCount);

	    // 게시글 목록 조회
	    List<BoardDTO> postList = boardService.findPosts(condition);

	    return handleSuccess(Map.of(
	        "data", postList,
	        "pageInfo", page
	    ));
	}
	
	@PostMapping("/board-write")
	public ResponseEntity<?> boardAdd(@RequestBody BoardDTO board, @AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			board.setUserId(userDetails.getMember().getId());
			board.setRegTime(LocalDateTime.now());
			System.out.println(board);
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
