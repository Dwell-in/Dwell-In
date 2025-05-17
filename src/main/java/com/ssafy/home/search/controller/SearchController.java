package com.ssafy.home.search.controller;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.search.model.service.DongService;
import com.ssafy.home.search.model.service.OpenGraphService;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController implements RestControllerHelper{
	private final DongService dongService;
	private final OpenGraphService openGraphService;
	// 외부 이미지를 가져오기 위한 RestTemplate
    private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@GetMapping("/sido")
	private ResponseEntity<?> sido(){
		try {
            List<String> list = dongService.findSidoList();
            return handleSuccess(list);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return handleFail(e);
        }
	}
	
	@GetMapping("/gugun/{sido}")
	private ResponseEntity<?> gugun(@PathVariable String sido){
		try {
			List<String> list = dongService.findGugunList(sido);
			return handleSuccess(list);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@GetMapping("/dong/{sido}/{gugun}")
	private ResponseEntity<?> dong(@PathVariable String sido, @PathVariable String gugun){
		try {
			List<String> list = dongService.findDongList(sido, gugun);
			return handleSuccess(list);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@GetMapping("/og")
	public Map<String, String> getOpenGraph(@RequestParam String url) {
		try {
			return openGraphService.fetchOpenGraphData(url);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@GetMapping("/imgUrl")
    public ResponseEntity<?> getImage(@RequestParam String url) {
        try {
            // 외부 이미지 URL로 GET 요청을 보내서 이미지 바이트 배열을 받음
            byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // JPEG 이미지를 가정 (다른 이미지 형식도 처리 가능)

            // 응답 본문에 이미지 바이트 배열을 포함
            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            // 이미지 데이터를 포함한 응답 반환
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return handleFail(e);
        }
    }
	
	// 네이버 검색 api
    @GetMapping("/naver/{category}")
    @ResponseBody
    public ResponseEntity<?> getBlogData(@PathVariable String category, @RequestParam String query, @RequestParam(required = false) Integer display) {
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String apiUrl = "https://openapi.naver.com/v1/search/"+category+".json?query=" + encodedQuery;
            if (display != null) {
            	apiUrl += "&display="+display;
            }

            Map<String, String> headers = new HashMap<>();
            headers.put("X-Naver-Client-Id", "2Pp8EaKyUrb_SEjaLj0y");
            headers.put("X-Naver-Client-Secret", "O2l3P61Wif");

            Map<String, Object> response = fetchJson(apiUrl, headers);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("API 요청 실패: " + e.getMessage());
        }
    }
	
	private Map<String, Object> fetchJson(String apiUrl, Map<String, String> headers) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
        conn.setRequestMethod("GET");
        headers.forEach(conn::setRequestProperty);

        try (InputStream is = conn.getResponseCode() == 200 ? conn.getInputStream() : conn.getErrorStream()) {
            return objectMapper.readValue(is, new TypeReference<>() {});
        } finally {
            conn.disconnect();
        }
    }
}
