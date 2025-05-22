package com.ssafy.home.house.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ssafy.home.common.CookieHelper;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class HouseCookieHandler implements CookieHelper{
	
	// 쿠키는 String 타입의 데이터만 저장 가능하므로 String 타입을 리스트로 전환
	public void updateRecentViewedCookie(String aptSeq, String recentViewed, HttpServletResponse res) {
		// 최근 방문한 페이지를 5개까지 저장하는 리스트
		List<String> list; 
		if(recentViewed==null) {
			list = new ArrayList<>();
		} 
		else {
			String decoded = URLDecoder.decode(recentViewed, StandardCharsets.UTF_8);
			list = new ArrayList<>(Arrays.asList(decoded.split(",")));
		}
		list.remove(aptSeq); // 리스트에 현재 아파트가 있으면 삭제
		list.add(0, aptSeq); // 맨 앞에 현재 아파트 번호 추가
		// 최대 5개만 유지
		if (list.size() > 10) {
			list = list.subList(0, 10);
		}
		// 다시 String 타입으로 변경해서 쿠키 저장
		String value = URLEncoder.encode(String.join(",", list), StandardCharsets.UTF_8);
		setupCookie("recentViewed", value, 60*60*24, "/", res);
	}
}
