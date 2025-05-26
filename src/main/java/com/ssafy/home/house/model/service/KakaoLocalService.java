package com.ssafy.home.house.model.service;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoLocalService {
	
	@Value("${kakao.client-id}")
	private String kakaoClientId;
	
	private final RestTemplate restTemplate;

	public Map<?,?> searchByCategory(double x, double y, String categoryCode, int radius) throws Exception{
	    URI uri = UriComponentsBuilder.newInstance()
	            .scheme("https")
	            .host("dapi.kakao.com")
	            .path("/v2/local/search/category.json")
	            .queryParam("x", x)
	            .queryParam("y", y)
	            .queryParam("radius", radius)
	            .queryParam("category_group_code", categoryCode)
	            .queryParam("sort", "distance")
	            .build()
	            .toUri();

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", "KakaoAK " + kakaoClientId);
	    HttpEntity<Void> entity = new HttpEntity<>(headers);

	    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

	    // JSON → Map 변환
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.readValue(response.getBody(), Map.class);
	}

}
