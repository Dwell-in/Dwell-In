package com.ssafy.home.security.jwt;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = jwtTokenProvider.resolveToken(request);

		if (token != null) {
			if (jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				 System.out.println("인증된 사용자: " + auth.getPrincipal());
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else {
				// 토큰이 유효하지 않을 때 401 응답
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getWriter(),  Map.of(
			            "status", "FAIL",
			            "error", "토큰이 유효하지 않거나 만료되었습니다."
			        ));
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
