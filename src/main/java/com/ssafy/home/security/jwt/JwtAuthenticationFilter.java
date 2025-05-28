package com.ssafy.home.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.member.model.dto.MemberDTO;
import com.ssafy.home.member.model.service.MemberService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberService memberService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
	    String token = jwtTokenProvider.resolveToken(request);
	    System.out.println("[필터] 추출된 access token: " + token);

	    if (token != null) {
	        String email = null;
	        try {
	            email = jwtTokenProvider.getUsername(token);
	            System.out.println("[필터] 토큰에서 추출한 email: " + email);
	        } catch (Exception e) {
	            System.out.println("[필터] getUsername() 실패: " + e.getMessage());
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return;
	        }

	        String refreshToken = memberService.getRefreshToken(email);
	        System.out.println("[필터] Redis에서 가져온 refreshToken: " + refreshToken);

	        String cookieRefreshToken = null;
	        if (request.getCookies() != null) {
	            for (Cookie cookie : request.getCookies()) {
	                if ("refreshToken".equals(cookie.getName())) {
	                    cookieRefreshToken = cookie.getValue();
	                    break;
	                }
	            }
	        }
	        System.out.println("[필터] 클라이언트 쿠키의 refreshToken: " + cookieRefreshToken);

	        if (cookieRefreshToken == null || !cookieRefreshToken.equals(refreshToken)) {
	            System.out.println("[필터] refresh 토큰 불일치");
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            new ObjectMapper().writeValue(response.getWriter(), Map.of("error", "다른 장치에서 로그인되었습니다"));
	            return;
	        }

	        if (jwtTokenProvider.validateToken(token)) {
	            System.out.println("[필터] access token 유효");
	            Authentication auth = jwtTokenProvider.getAuthentication(token);
	            SecurityContextHolder.getContext().setAuthentication(auth);
	        } else {
	            System.out.println("[필터] access token 만료 → 프론트에게 401 응답");
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return;
	        }
	    }

	    filterChain.doFilter(request, response);
	}
	
	// 로그인이 필요한 곳에만 필터 적용
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	    String path = request.getRequestURI();
	    String method = request.getMethod();

	    // 댓글 조회 허용
	    if (path.startsWith("/api/v1/comment") && "GET".equals(method)) {
	        return true;
	    }
	    // 매물 조회 허용
	    if (path.startsWith("/api/v1/property") && "GET".equals(method)) {
	        return true;
	    }
	    // 로그인 필요 없는 나머지 경로
	    return !(
	        path.startsWith("/api/v1/ai")
	        || path.startsWith("/api/v1/predict")
	        || path.startsWith("/api/v1/board/board-write")
	        || path.startsWith("/api/v1/board/board-delete")
	        || path.startsWith("/api/v1/board/board-update")
	        || path.startsWith("/api/v1/member/update")
	        || path.startsWith("/api/v1/member/delete")
	        || path.startsWith("/api/v1/member/user-info")
	        || path.startsWith("/api/v1/starred")
	        || path.startsWith("/api/v1/comment")
	        || path.startsWith("/api/v1/property")
	        || path.startsWith("/chat")
	        || path.startsWith("/api/v1/memberFavorite")
	    );
	}
}

