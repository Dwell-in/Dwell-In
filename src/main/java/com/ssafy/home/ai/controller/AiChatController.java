package com.ssafy.home.ai.controller;

import com.ssafy.home.ai.model.dto.AptRecommendDTO;
import com.ssafy.home.house.model.dto.CompareDetailRequestDTO;
import com.ssafy.home.house.model.dto.FilteredPropertyDTO;
import com.ssafy.home.member.favorite.model.dto.MemberFavoriteDTO;
import com.ssafy.home.member.favorite.model.service.MemberFavoriteService;
import com.ssafy.home.security.dto.CustomUserDetails;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.ai.prompt.AiPrompt;
import com.ssafy.home.ai.service.AiChatService;
import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.house.model.dto.ComparePriceRequestDTO;
import com.ssafy.home.house.model.dto.CompareSeqNmRequestDTO;
import com.ssafy.home.house.model.dto.HouseinfoDTO;
import com.ssafy.home.house.model.dto.HouserdealDTO;
import com.ssafy.home.house.model.service.HouseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiChatController implements RestControllerHelper {
	
	private final AiChatService aService;
	private final HouseService hService;
	private final AiPrompt aPrompt;
	private final MemberFavoriteService memberFavoriteService;
	
	@PostMapping("/simple")
	ResponseEntity<?> simpleGeneration(@RequestBody Map<String, String> userInput){
		System.out.println("컨트롤러 도착 ==================");
		Object result = aService.simpleGenration(userInput.get("message"));
		System.out.println(result);
		return handleSuccess(Map.of("message",result));
	}
	
	@PostMapping("/price")
	ResponseEntity<?> priceGeneratioin(@RequestBody ComparePriceRequestDTO request){
		
		List<CompareSeqNmRequestDTO> aptList = request.getAptList();
		
	
		List<HouseinfoDTO> aptDealList = hService.findInfoList(aptList);
		String prompt = aPrompt.buildComparePrompt(aptDealList);
		
		Object result = aService.simpleGenration(prompt);
		
		return handleSuccess(Map.of("message",result));
	}

	@PostMapping("/detail")
	ResponseEntity<?> detailGeneration(@RequestBody List<CompareDetailRequestDTO> request){

		String prompt = aPrompt.buildDetailPrompt(request);

		Object result = aService.simpleGenration(prompt);

		return handleSuccess(Map.of("message", result));
	}

	@PostMapping("/filtered")
	public ResponseEntity<?> filterGeneration(@RequestBody List<FilteredPropertyDTO> filteredList, @AuthenticationPrincipal
												 CustomUserDetails member) {
		long memberId = member.getMember().getId();
		MemberFavoriteDTO memberFavoriteDTO = memberFavoriteService.getMemberFavorite(memberId);

		String prompt = aPrompt.buildFilterRecommendPrompt(memberFavoriteDTO,filteredList);
		// 실제로는 AI 모델 호출 → 추천 결과 리스트 반환
		Object result = aService.simpleGenration(prompt);

		return ResponseEntity.ok().body(Map.of("data", result));
	}

	@PostMapping("/apt-recommend")
	public ResponseEntity<?> recommend(@RequestBody List<AptRecommendDTO> aptList, @AuthenticationPrincipal CustomUserDetails member) {

		long memberId = member.getMember().getId();
		MemberFavoriteDTO memberFavoriteDTO = memberFavoriteService.getMemberFavorite(memberId);

		String prompt = aPrompt.buildAptRecommendationPrompt(memberFavoriteDTO,aptList);
		Object result = aService.simpleGenration(prompt);

		return ResponseEntity.ok().body(Map.of("data", result));
	}






}
