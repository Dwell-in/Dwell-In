package com.ssafy.home.security.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.member.model.service.MemberService;
import com.ssafy.home.security.dto.LoginRequest;
import com.ssafy.home.security.jwt.JwtTokenProvider;
import com.ssafy.home.security.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements RestControllerHelper {
	
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService cService;
    private final MemberService mService;
    
    @Value("${frontend.url}")
    private String frontendUrl;

    @PostMapping("/login")
    // json 요청이면 @RequestBody, form이면 @ModelAttribute
    public ResponseEntity<?> login(@RequestBody LoginRequest dto) {
        try {
        	// 인증 시도
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );

            // 토큰 생성
            String token = jwtTokenProvider.createToken(
                dto.getEmail(),
                authentication.getAuthorities()
            );

            // 응답
            return ResponseEntity.ok(Map.of("token", token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid email or password"));
        }
    }
    
	@GetMapping("/kakao/access-token")
	public ResponseEntity<?> kakaoCallback(@RequestParam String code){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "7abe84cfdb3ff3310feaca8aa90809c0");
		params.add("redirect_uri", frontendUrl+"/member/login");
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
			Authentication auth = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
			
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
    
}
