package com.ssafy.home.security.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

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
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else {
				// 토큰이 유효하지 않을 때 401 응답
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}
