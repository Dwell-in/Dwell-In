package com.ssafy.home.member.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.member.model.dto.MemberDTO;
import com.ssafy.home.member.model.service.MemberService;
import com.ssafy.home.security.dto.CustomUserDetails;
import com.ssafy.home.security.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
// CookieHelper import 경로 수정해야 함
public class MemberController implements RestControllerHelper {

	private final MemberService mService;
	private final PasswordEncoder pe;

	@PostMapping("/signup")
	public ResponseEntity<?> memberAdd(@ModelAttribute MemberDTO member,
			@RequestParam(required = false) MultipartFile img, HttpSession session) {
		try {
			String hashpw = pe.encode(member.getPassword());
			if (img != null && !img.isEmpty()) {
				member.setProfile(img.getBytes());
			}
			member.setRole("USER");
			member.setPassword(hashpw);
			mService.addMember(member);
			return handleSuccess(member, HttpStatus.CREATED);
		} catch (RuntimeException | IOException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}

	@PostMapping("/update")
	public ResponseEntity<?> memberModify(@ModelAttribute MemberDTO member,
			@AuthenticationPrincipal CustomUserDetails details) {
		try {
			String hashpw = pe.encode(member.getPassword());
			member.setPassword(hashpw);
			mService.modifyMember(member);
			if (details.getUsername().equals(member.getEmail())) {
				details.setMember(member);
			}
			return handleSuccess(member);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam String email, HttpSession session, RedirectAttributes redir) {
		try {
			mService.removeMember(email);
			session.removeAttribute("loginUser");
			return handleSuccess("삭제 성공");
		} catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}

	@PostMapping("/password-find")
	public ResponseEntity<?> passwordFind(@RequestParam String email, HttpSession session) {
		try {
			MemberDTO member = mService.findMemberDetail(email);
			if (member != null) {
				String hashpw = pe.encode("12345");
				member.setPassword(hashpw);
				mService.modifyMember(member);
				return handleSuccess(Map.of("password", "12345"));
			} else
				return handleSuccess(Map.of("member", "null"));
		} catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}

	@PostMapping("/check-email")
	public Map<String, Boolean> checkEmailDuplicate(@RequestParam String email, HttpServletResponse response) {
		// mService 또는 DAO에 이메일 존재 여부를 확인하는 메서드가 있다고 가정합니다.
		MemberDTO member = mService.findMemberDetail(email);
		boolean exists = member != null;
		System.out.println("입력받은 이메일: " + email);

		return Map.of("exists", exists);
	}

	@GetMapping("/user-info")
	public ResponseEntity<?> getCurrentUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
		MemberDTO member = userDetails.getMember();
		return handleSuccess(Map.of("id", member.getId(), "email", member.getEmail(), "name", member.getName(),
				"profileImg", member.getProfileImg(), "role", member.getRole()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getMemberInfo(@PathVariable int id) {
		MemberDTO member = mService.findMemberById(id);
		return handleSuccess(member);
	}

//	@PostMapping("/check-email")
//	public ResponseEntity<?> checkEmailDuplicate(@RequestParam String email, HttpServletResponse response) {
//		// mService 또는 DAO에 이메일 존재 여부를 확인하는 메서드가 있다고 가정합니다.
//		MemberDTO member = mService.findMemberDetail(email);
//		boolean exists = member != null;
//		System.out.println("입력받은 이메일: " + email);
//
//		return handleSuccess(Map.of("exists", exists));
//	}
//	

}
