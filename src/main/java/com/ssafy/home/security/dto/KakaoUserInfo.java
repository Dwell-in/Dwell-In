package com.ssafy.home.security.dto;

import java.util.Map;

import lombok.Data;

@Data
public class KakaoUserInfo {
	private Long id;
	private Map<String, Object> kakao_account;
}
