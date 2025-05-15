package com.ssafy.home.security.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ssafy.home.security.dto.KakaoUserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    @Value("${frontend.url}")
    private String frontendUrl;
    @Value("${kakao.client-id}")
    private String kakaoClientId;
    
    public String requestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", frontendUrl + "/member/login");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate rt = new RestTemplate();

        ResponseEntity<Map> response = rt.postForEntity(
            "https://kauth.kakao.com/oauth/token", request, Map.class
        );
        return (String) response.getBody().get("access_token");
    }

    public KakaoUserInfo requestUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();

        ResponseEntity<KakaoUserInfo> response = rt.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET,
            request,
            KakaoUserInfo.class
        );
        return response.getBody();
    }
}

