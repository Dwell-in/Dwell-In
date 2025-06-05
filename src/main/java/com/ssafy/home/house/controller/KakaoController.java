package com.ssafy.home.house.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.house.model.service.KakaoLocalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kakao")
public class KakaoController implements RestControllerHelper{

    private final KakaoLocalService kakaoLocalService;

    @GetMapping("/local-search")
    public ResponseEntity<?> searchByCategory(
            @RequestParam double x,
            @RequestParam double y,
            @RequestParam String categoryCode,
            @RequestParam(defaultValue = "1000") int radius) {
        Map<?, ?> result;
		try {
			result = kakaoLocalService.searchByCategory(x, y, categoryCode, radius);
			return handleSuccess(result);
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
    }
}
