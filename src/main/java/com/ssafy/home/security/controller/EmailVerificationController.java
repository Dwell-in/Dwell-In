package com.ssafy.home.security.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.member.model.dto.MemberDTO;
import com.ssafy.home.member.model.service.MemberService;
import com.ssafy.home.security.dto.EmailVerificationTokenDTO;
import com.ssafy.home.security.service.EmailVerificationService;
import com.ssafy.home.security.service.MailService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailVerificationController implements RestControllerHelper{
	
	private final EmailVerificationService eService;
	private final MailService mailService;
	private final MemberService mService;
	private final PasswordEncoder pe;
	
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
		return handleSuccess(Map.of("email",tokenData.getEmail(),"success",success,"msg",msg));
	}
	
	@PostMapping("/send-token")
	public ResponseEntity<?> sendToken(@RequestParam String email, HttpServletRequest request) {
	    try {
	        if (email.isBlank() || !email.contains("@")) return handleSuccess("유효하지 않은 이메일입니다");
	        if (mService.findMemberDetail(email) != null) return handleSuccess("이미 존재하는 이메일입니다");

	        String token = eService.createVerificationToken(email);

	        // Origin 헤더에서 프론트 URL 추출
	        String origin = request.getHeader("Origin");
	        String frontUrl = (origin != null) ? origin : "http://localhost:8080"; // 기본값

	        mailService.sendVerificationEmail(email, token, frontUrl);

	        return handleSuccess("메일이 전송되었습니다");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return handleFail(e);
	    }
	}
	
	@PostMapping("/send-password")
	public ResponseEntity<?> sendTempPassword(@RequestParam String email) {
		try {
			if(email.isBlank() || !email.contains("@")) return handleSuccess("유효하지 않은 이메일입니다");
			MemberDTO member = mService.findMemberDetail(email);
			if(member==null) return handleSuccess("존재하지 않는 이메일입니다");
			String tempPassword = UUID.randomUUID().toString().substring(0, 10);
			String hashPassword = pe.encode(tempPassword);
			member.setPassword(hashPassword);
			mService.modifyMember(member);
			
			mailService.sendTempPassword(email, tempPassword);
			return handleSuccess("임시 비밀번호가 발급되었습니다.\n이메일을 확인해주세요.");
		}catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
}
