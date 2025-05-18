package com.ssafy.home.security.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.member.model.service.MemberService;
import com.ssafy.home.security.dto.KakaoUserInfo;
import com.ssafy.home.security.dto.LoginRequest;
import com.ssafy.home.security.jwt.JwtTokenProvider;
import com.ssafy.home.security.service.CustomUserDetailsService;
import com.ssafy.home.security.service.KakaoAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements RestControllerHelper {
	
    private final AuthenticationManager authenticationManager;
    private final MemberService mService;
    private final KakaoAuthService kService;
    private final CustomUserDetailsService cService;
    private final JwtTokenProvider jwtTokenProvider;
    
    @Value("${frontend.url}")
    private String frontendUrl;
    @Value("${kakao.client-id}")
    private String kakaoClientId;

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

	@GetMapping("/kakao/login")
	public ResponseEntity<?> kakaoLogin(@RequestParam String code) {
	    String accessToken = kService.requestAccessToken(code);
	    KakaoUserInfo kakaoUser = kService.requestUserInfo(accessToken);

	    String email = mService.findEmailByKakaoId(kakaoUser.getId()+"");
	    System.out.println(kakaoUser);
	    Map<String, String> profile = (Map) kakaoUser.getKakao_account().get("profile");
        if (email==null || email.isBlank()) {
            // 프론트에서 회원가입 페이지로 유도 
        	return handleSuccess(Map.of("signup",true,
        			"kakaoId", kakaoUser.getId(),
        			"name", profile.get("nickname")));
        }

	    // 사용자 인증 객체 생성
        // 이미 인증된 사용자이므로 굳이 authenticationManager를 거치지 않아도 됨
	    UserDetails userDetails = cService.loadUserByUsername(email);
	    Authentication auth = new UsernamePasswordAuthenticationToken(
	        userDetails, null, userDetails.getAuthorities()
	    );

	    // JWT 생성
	    String token = jwtTokenProvider.createToken(email, auth.getAuthorities());

	    return handleSuccess(Map.of("signup",false,"token", token));
	}

    
}
