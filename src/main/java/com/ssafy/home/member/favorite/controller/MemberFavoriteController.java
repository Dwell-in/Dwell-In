package com.ssafy.home.member.favorite.controller;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.member.favorite.model.dto.MemberFavoriteDTO;
import com.ssafy.home.member.favorite.model.dto.MemberFavoriteJsonDTO;
import com.ssafy.home.member.favorite.model.service.MemberFavoriteService;
import com.ssafy.home.security.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/memberFavorite")
@RequiredArgsConstructor
public class MemberFavoriteController implements RestControllerHelper {
	
	private final MemberFavoriteService memberFavoriteService;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	@PostMapping
	public ResponseEntity<?> MemberFavoriteAdd(@RequestBody MemberFavoriteJsonDTO jsonDto,@AuthenticationPrincipal CustomUserDetails member){
	
		System.out.println(jsonDto);
		MemberFavoriteDTO dto= MemberFavoriteDTO.builder()
        .memberId((long)(member.getMember().getId()))
        .preferredLocation(jsonDto.getPreferredLocation())
        .budgetMin(getLongFromList(jsonDto.getBudgetRange(), 0))
        .budgetMax(getLongFromList(jsonDto.getBudgetRange(), 1))
        .rentMin(getLongFromList(jsonDto.getRentRange(), 0))
        .rentMax(getLongFromList(jsonDto.getRentRange(), 1))
        .preferredType(jsonDto.getPreferredType())
        .minArea(jsonDto.getSpaceRequirements().getMinArea())
        .minRooms(jsonDto.getSpaceRequirements().getMinRooms())
        .commuteTarget(jsonDto.getCommuteTarget())
        .commuteTimeLimit(jsonDto.getCommuteTimeLimit())
        .householdType(jsonDto.getHouseholdType())
        .floorPreference(jsonDto.getFloorPreference())
        .buildYearPreference(jsonDto.getBuildYearPreference())
        .requiredFeatures(toJson(jsonDto.getRequiredFeatures()))
        .lifestylePriority(toJson(jsonDto.getLifestylePriority()))
        .build();
		try {
			memberFavoriteService.addFavorite(dto);
			return handleSuccess(HttpStatus.CREATED);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	 private static String toJson(List<String> list) {
	      
	            try {
					return objectMapper.writeValueAsString(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
	        
	    }
	 private static Long getLongFromList(List<Long> list, int index) {
	        return list != null && list.size() > index ? list.get(index) : null;
	    }


}
