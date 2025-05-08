package com.ssafy.home.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.home.common.interceptor.AuthInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MvcConfigurer implements WebMvcConfigurer {
	private final AuthInterceptor authI;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authI)
			.addPathPatterns("/movie/**")
//			.addRequestMethods(RequestMethod.GET)	// 따로 get, post 구분하고 싶으면 추가
			.addPathPatterns("/member/myPage", "/member/modify", "/member/remove")
			.excludePathPatterns("/**/error**", "/movie/list");
	}
}
