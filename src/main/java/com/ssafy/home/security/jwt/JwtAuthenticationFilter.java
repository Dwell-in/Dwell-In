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

		String token = jwtTokenProvider.resolveToken(request);

		if (token != null) {
		    if (jwtTokenProvider.validateToken(token)) {
		        // 정상 토큰 → 인증
		        Authentication auth = jwtTokenProvider.getAuthentication(token);
		        SecurityContextHolder.getContext().setAuthentication(auth);
		    } else {
		        try {
		            // 만료된 토큰일 경우 email 추출
		            String email = jwtTokenProvider.getUsername(token);
		            MemberDTO member = memberService.findMemberDetail(email);
		            String refreshToken = member.getRefreshToken();

		            if (jwtTokenProvider.validateToken(refreshToken)) {
		                // 리프레시 토큰도 유효하면 Access Token 재발급
		                String newAccessToken = jwtTokenProvider.createToken(
		                    email,
		                    List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole()))
		                );

		                // 새 토큰 헤더로 응답
		                response.setHeader("X-New-Access-Token", newAccessToken);

		                // 인증 처리
		                Authentication auth = jwtTokenProvider.getAuthentication(newAccessToken);
		                SecurityContextHolder.getContext().setAuthentication(auth);
		            } else {
		                // 리프레시 토큰도 만료된 경우
		                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		                new ObjectMapper().writeValue(response.getWriter(), Map.of("error", "Refresh Token도 만료됨"));
		                return;
		            }
		        } catch (JwtException e) {
		            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		            new ObjectMapper().writeValue(response.getWriter(), Map.of("error", "Access Token 파싱 실패"));
		            return;
		        }
		    }
		}
		filterChain.doFilter(request, response);
	}
}
