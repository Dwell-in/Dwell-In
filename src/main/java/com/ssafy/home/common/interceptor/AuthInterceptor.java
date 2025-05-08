package com.ssafy.home.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser") != null) {
			// true: 통과
			return true;
		} else {
			// false: 페이지 진입 실패 => 실패시 이동할 경로 써주기
			response.sendRedirect(request.getContextPath()+"/member/login");
			return false;
		}
		
	}
}
