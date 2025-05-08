package com.ssafy.home.common.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	// 회원가입
	@GetMapping("/member/signup")
	public String signupPage() {
		return "member/signup";
	}

	// 로그인/로그아웃
	@GetMapping("/member/login")
	public String loginPage(@RequestParam(required=false) String code,
			Model model) {
		if(code != null) model.addAttribute("code",code);
		return "member/login";
	}

	@GetMapping("/member/mypage")
	public String myPage() {
		return "member/mypage";
	}

	@GetMapping("/member/update")
	public String updatePage() {
		return "member/update";
	}

	// 비밀번호 찾기
	@GetMapping("/member/password-find")
	public String findPage() {
		return "member/passwordFind";
	}
}
