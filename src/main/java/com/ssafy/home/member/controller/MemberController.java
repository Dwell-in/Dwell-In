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
	private final CustomUserDetailsService cService;
	private final PasswordEncoder pe;

	@PostMapping("/signup")
	public ResponseEntity<?> memberAdd(@ModelAttribute MemberDTO member, 
			@RequestParam(required = false) MultipartFile img, HttpSession session) {
		try {
			String hashpw = pe.encode(member.getPassword());
			if(img != null && !img.isEmpty()) {
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
			if(details.getUsername().equals(member.getEmail())) {
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
	
	@GetMapping("/kakao/access-token")
	public ResponseEntity<?> kakaoCallback(@RequestParam String code){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "7abe84cfdb3ff3310feaca8aa90809c0");
		params.add("redirect_uri", "http://localhost:8080/member/login");
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> response = rt.postForEntity(
		    "https://kauth.kakao.com/oauth/token", request, String.class
		);
		return response;
	}
	
	@GetMapping("/kakao/user-info")
	public ResponseEntity<?> getUserInfo(@RequestParam String accessToken){
		HttpHeaders userInfoHeaders = new HttpHeaders();
		RestTemplate rt = new RestTemplate();
		userInfoHeaders.setBearerAuth(accessToken);

		HttpEntity<Void> userInfoRequest = new HttpEntity<>(userInfoHeaders);

		ResponseEntity<String> userInfoResponse = rt.exchange(
		    "https://kapi.kakao.com/v2/user/me",
		    HttpMethod.GET,
		    userInfoRequest,
		    String.class
		);
		return userInfoResponse;
	}
	
	@GetMapping("/kakao/login")
	public ResponseEntity<?> getUserInfo(@RequestParam String id, @RequestParam String nickname,
			@RequestParam String profileImage, HttpSession session){
		String email = mService.findEmailByKakaoId(id);
		try {
			UserDetails member = cService.loadUserByUsername(email);
			Authentication auth = new UsernamePasswordAuthenticationToken(member.getUsername(), null, member.getAuthorities());
			
			SecurityContext context = SecurityContextHolder.getContext();
	        context.setAuthentication(auth);
	        
	        // 필터 체인을 적용하지 않기 때문에 수동으로 세션 scope에 저장해야 함
	        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
	        
	        return handleSuccess(Map.of("redirect", "/"));
		} catch (Exception e) {
			return handleSuccess(Map.of(
		            "redirect", "/member/signup",
		            "id", id,
		            "name", nickname,
		            "profileImage", profileImage
		        ));
		}
	}
	
	@GetMapping("/user-info")
	public ResponseEntity<?> getCurrentUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails){
		MemberDTO member = userDetails.getMember();
		return handleSuccess(Map.of("email",member.getEmail(),"name",member.getName(),"profileImg",member.getProfileImg()));
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
