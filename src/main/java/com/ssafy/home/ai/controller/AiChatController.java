package com.ssafy.home.ai.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.ai.service.AiChatService;
import com.ssafy.home.common.RestControllerHelper;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiChatController implements RestControllerHelper {
	
	private final AiChatService aService;
	
	@PostMapping("/simple")
	ResponseEntity<?> simpleGeneration(@RequestBody Map<String, String> userInput){
		System.out.println("컨트롤러 도착 ==================");
		Object result = aService.simpleGenration(userInput.get("message"));
		System.out.println(result);
		return handleSuccess(Map.of("message",result));
	}

}
