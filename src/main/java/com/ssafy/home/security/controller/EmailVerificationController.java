package com.ssafy.home.security.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.security.dto.EmailVerificationTokenDTO;
import com.ssafy.home.security.service.EmailVerificationService;
import com.ssafy.home.security.service.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailVerificationController implements RestControllerHelper{
	
	private final EmailVerificationService eService;
	private final MailService mailService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> getMethodName(@RequestParam String token) {
		EmailVerificationTokenDTO tokenData = eService.findbyToken(token);
		String msg = "이메일 인증이 완료되었습니다";
		boolean success = false; 
		if(tokenData==null) msg = "유효하지 않은 링크입니다";
		else if(tokenData.isUsed()) msg = "이미 사용된 링크입니다";
		else if (tokenData.getExpiryDate().isBefore(LocalDateTime.now())) {
            msg =  "인증 링크가 만료되었습니다.";
        } else success = true;

		eService.markTokenAsUsed(token);
		return handleSuccess(Map.of("success",success,"msg",msg));
	}
	
	@PostMapping("/send-token")
	public ResponseEntity<?> sendToken(@RequestParam String email) {
		try {
			String token = eService.createVerificationToken(email);
			mailService.sendVerificationEmail(email, token);
			return handleSuccess("메일이 전송되었습니다");
		}catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
}
