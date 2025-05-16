package com.ssafy.home.starred.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.member.model.dto.MemberDTO;
import com.ssafy.home.security.dto.CustomUserDetails;
import com.ssafy.home.starred.model.dto.StarredDTO;
import com.ssafy.home.starred.model.dto.StarredHouseInfoDTO;
import com.ssafy.home.starred.model.service.StarredService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/starred")
@RequiredArgsConstructor
@Slf4j
public class StarredController implements RestControllerHelper {

	private final StarredService sService;
	
	@PostMapping("/{aptSeq}")
	public ResponseEntity<?> starredAdd(@PathVariable String aptSeq , @AuthenticationPrincipal CustomUserDetails member){
		try {
			
			int memberId = member.getMember().getId();
			StarredDTO starred = StarredDTO.builder().userId(memberId).aptSeq(aptSeq).build();
			sService.addStarred(starred);
			return handleSuccess("등록성공",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return handleFail(e); 
		}
		
	}
	
	@GetMapping
	public ResponseEntity<?> starredList(@AuthenticationPrincipal CustomUserDetails member){
		try {
			System.out.println("관심지역리스트 도착");
			int memberId = member.getMember().getId();
			StarredDTO starred = StarredDTO.builder().userId(memberId).build();
			List<StarredHouseInfoDTO> starredList = sService.findStarred(starred);
			System.out.println(starredList);
			return handleSuccess(Map.of("data", starredList));
		}catch(Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
		
		
	}
	
	@DeleteMapping("/{aptSeq}")
	public ResponseEntity<?> starredRemove(@PathVariable String aptSeq, @AuthenticationPrincipal CustomUserDetails member){
		try {
			int memberId = member.getMember().getId();
			StarredDTO starred = StarredDTO.builder().userId(memberId).aptSeq(aptSeq).build();
			sService.removeStarred(starred);
			return handleSuccess("삭제성공",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
		
		
	}
}
