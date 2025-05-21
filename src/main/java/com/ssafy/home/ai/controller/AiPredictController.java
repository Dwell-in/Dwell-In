package com.ssafy.home.ai.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.ai.prompt.AiPrompt;
import com.ssafy.home.ai.service.AiChatService;
import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.house.model.dto.HouseSeqDTO;
import com.ssafy.home.house.model.dto.HouseinfoDTO;
import com.ssafy.home.house.model.service.HouseService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/predict")
@RequiredArgsConstructor
public class AiPredictController implements RestControllerHelper {

	private final AiChatService aService;
	private final HouseService hService;
	private final AiPrompt aPrompt;
	
	@PostMapping("/price")
	ResponseEntity<?> pricePredictGeneration(@RequestBody HouseSeqDTO aptSeq){	
		
		HouseinfoDTO houseInfo = hService.findInfo(aptSeq.getAptSeq());
		String prompt = aPrompt.buildPricePredictPrompt(houseInfo);
		Object result = aService.simpleGenration(prompt);
		System.out.println(result);
		return handleSuccess(Map.of("message",result));
	}
}
