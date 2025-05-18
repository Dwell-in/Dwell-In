package com.ssafy.home.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.home.board.NotWrittenMember;
import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.dto.PostSearchCondition;
import com.ssafy.home.board.model.service.BoardServiceImpl;
import com.ssafy.home.security.dto.CustomUserDetails;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardServiceImpl boardService;

	@GetMapping("/notification-list")
	public String boardList(Model model) {
		List<BoardDTO> boardList = boardService.findPosts(new PostSearchCondition());
		model.addAttribute("boardList", boardList);
		return "board/list";
	}

	@GetMapping("/board-detail")
	public String detail(@RequestParam int boardId, Model model) {
		BoardDTO board = boardService.findDetailBoard(boardId);
		model.addAttribute("board", board);
		return "board/detail";
	}

	@GetMapping("/board-write-form")
	public String boardAddPage(Model model, HttpSession session) {
		return "board/regist";
	}

//	@GetMapping("/board-search")
//	public String boardSearch(@RequestParam String search, Model model){
//		List<BoardDTO> boardList = boardService.findSearchBoard(search);
//		model.addAttribute("boardList",boardList);
//		return "board/list";
//	}

//	@PostMapping("/board-write")
//	public String boardAdd(@ModelAttribute BoardDTO boardDto) {
//		boardDto.setRegDate(LocalDateTime.now());
//		boardService.addBoard(boardDto);
//		return "redirect:/board/notification-list";
//	}

	@GetMapping("/board-update-page")
	public String boardModifyPage(@RequestParam int boardId, Model model,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		BoardDTO board = boardService.findDetailBoard(boardId);
		System.out.println(board);
		if (board.getUserId() == userDetails.getMember().getId()) {
			model.addAttribute("board", board);
			return "board/update";
		} else
			throw new NotWrittenMember();
	}

//	@PostMapping("/board-delete")
//	public String boardRemove(@RequestParam int boardId) {
//		boardService.removeBoard(boardId);
//		return "redirect:/board/notification-list";
//	}

//	@PostMapping("/board-update")
//	public String boardModify(@ModelAttribute BoardDTO boardDTO){
//		boardService.modifyBoard(boardDTO);
//		return "redirect:/board/notification-list";
//	}

}
