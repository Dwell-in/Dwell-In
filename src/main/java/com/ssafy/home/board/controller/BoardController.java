package com.ssafy.home.board.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.home.board.model.dto.BoardDTO;
import com.ssafy.home.board.model.service.BoardService;
import com.ssafy.home.member.model.dto.MemberDTO;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	
	
	@GetMapping("/notification-list")
	public String boardList(Model model) {
		List<BoardDTO> boardList = boardService.findBoard();
		model.addAttribute("boardList", boardList);
		return "board/list";
	}
	
	@GetMapping("/board-detail")
	public String boardDetails() {
		return "board/detail";
	}
	
	@GetMapping("/board-write-form")
	public String boardAddPage(Model model, HttpSession session) {
		MemberDTO member = (MemberDTO)session.getAttribute("loginUser");
		model.addAttribute("email",member.getEmail());
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
	public String boardModifyPage(@RequestParam int boardId, Model model) {
		BoardDTO board = boardService.findDetailBoard(boardId);
		model.addAttribute("board",board);
		return "board/update";
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
