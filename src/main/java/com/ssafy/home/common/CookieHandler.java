package com.ssafy.home.common;

import java.time.Duration;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieHandler {

	private boolean isProd() {
		return "prod".equals(System.getenv("SPRING_PROFILES_ACTIVE"));
	}

	public void addCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds,
			boolean httpOnly) {

		boolean prod = isProd();

		ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(name, value)
				.maxAge(Duration.ofSeconds(maxAgeSeconds)).httpOnly(httpOnly).secure(prod).path("/");

		if (prod) {
			builder.sameSite("None");
		}

		response.addHeader("Set-Cookie", builder.build().toString());
	}
}
