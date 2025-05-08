package com.ssafy.home.house.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/house")
public class MoveController {
	// 회원가입
	@GetMapping("/map")
	public String forwardMap() {
		return "map";
	}
}
