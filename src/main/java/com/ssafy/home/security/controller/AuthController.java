package com.ssafy.home.security.controller;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.CookieHandler;
import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.member.model.service.MemberService;
import com.ssafy.home.security.dto.KakaoUserInfo;
import com.ssafy.home.security.dto.LoginRequest;
import com.ssafy.home.security.jwt.JwtTokenProvider;
import com.ssafy.home.security.service.CustomUserDetailsService;
import com.ssafy.home.security.service.KakaoAuthService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
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
	private final CookieHandler cookieHandler;

	@Value("${kakao.client-id}")
	private String kakaoClientId;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest dto, HttpServletResponse response) {
	    Authentication auth = authenticationManager
	            .authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

	    String accessToken = jwtTokenProvider.createToken(dto.getEmail(), auth.getAuthorities());
	    String refreshToken = jwtTokenProvider.createRefreshToken(dto.getEmail());

	    // Redis에 refresh 토큰 저장
	    mService.modifyRefreshToken(dto.getEmail(), refreshToken);

	    // 공통 쿠키 핸들러 사용
	    cookieHandler.addCookie(response, "refreshToken", refreshToken, (int) Duration.ofDays(7).getSeconds(), true);

	    return handleSuccess(Map.of("accessToken", accessToken));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@CookieValue(required = false) String refreshToken) {
		try {
			// refreshToken이 null이면 신경 x
			if (refreshToken != null) {
				String email = jwtTokenProvider.getUsername(refreshToken);
				mService.removeRefreshToken(email);
			}
			return handleSuccess(Map.of("message", "로그아웃 성공"));
		} catch (JwtException e) {
			// 얘도 어차피 토큰이 기능을 못하니까 그냥 두기
			return handleSuccess(Map.of("message", "유효하지 않은 토큰"));
		}
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshAccessToken(@CookieValue(required = false) String refreshToken) {
		if (refreshToken == null) {
			return handleSuccess(Map.of("message", "Refresh 토큰 없음"), HttpStatus.UNAUTHORIZED);
		}

		try {
			String email = jwtTokenProvider.getUsername(refreshToken);
			String storedToken = mService.getRefreshToken(email);

			if (!refreshToken.equals(storedToken)) {
				return handleSuccess(Map.of("message", "다른 장치에서 로그인됨"), HttpStatus.UNAUTHORIZED);
			}

			if (!jwtTokenProvider.validateToken(refreshToken)) {
				return handleSuccess(Map.of("message", "Refresh 토큰 만료됨"), HttpStatus.UNAUTHORIZED);
			}

			String newAccessToken = jwtTokenProvider.createToken(email, List.of());
			return handleSuccess(Map.of("accessToken", newAccessToken));

		} catch (JwtException e) {
			return handleSuccess(Map.of("message", "Refresh 토큰 파싱 실패"), HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/kakao/login")
	public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) {
	    String accessToken = kService.requestAccessToken(code);
	    KakaoUserInfo kakaoUser = kService.requestUserInfo(accessToken);

	    String email = mService.findEmailByKakaoId(kakaoUser.getId() + "");
	    Map<String, String> profile = (Map) kakaoUser.getKakao_account().get("profile");

	    if (email == null || email.isBlank()) {
	        return handleSuccess(Map.of("signup", true, "kakaoId", kakaoUser.getId(), "name", profile.get("nickname")));
	    }

	    UserDetails userDetails = cService.loadUserByUsername(email);
	    Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

	    String token = jwtTokenProvider.createToken(email, auth.getAuthorities());
	    String refreshToken = jwtTokenProvider.createRefreshToken(email);
	    mService.modifyRefreshToken(email, refreshToken);

	    cookieHandler.addCookie(response, "refreshToken", refreshToken, (int) Duration.ofDays(7).getSeconds(), true);

	    Map<String, Object> responseBody = Map.of("status", "SUCCESS", "data", Map.of("signup", false, "token", token));
	    return ResponseEntity.ok(responseBody);
	}

}
