package com.ssafy.home.house.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.house.model.dto.HouseinfoDTO;
import com.ssafy.home.house.model.service.HouseService;
import com.ssafy.home.house.util.HouseCookieHandler;
import com.ssafy.home.search.model.dto.DongDTO;
import com.ssafy.home.security.dto.CustomUserDetails;
import com.ssafy.home.starred.model.dto.StarredDTO;
import com.ssafy.home.starred.model.service.StarredService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/house")
@RequiredArgsConstructor
public class HouseController implements RestControllerHelper{
	private final HouseService hService;
	private final HouseCookieHandler cHandler;
	private final StarredService sService;

	@GetMapping("/{aptSeq}")
	private ResponseEntity<?> house(@PathVariable String aptSeq){
		try {
			HouseinfoDTO info = hService.findInfo(aptSeq);
			return handleSuccess(info);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@GetMapping
	private ResponseEntity<?> houseList(@ModelAttribute DongDTO dongDTO){
		try {
            List<HouseinfoDTO> list = hService.findInfoList(dongDTO);
            return handleSuccess(list);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return handleFail(e);
        }
	}
	
	@GetMapping("/in-bound")
	private ResponseEntity<?> getHousesInBounds(double swLat, double swLng, double neLat, double neLng) {
	    try {
	        List<HouseinfoDTO> houses = hService.findInBounds(swLat, swLng, neLat, neLng);
	        System.out.println(houses.size());
	        System.out.println(houses);
	        return handleSuccess(houses);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return handleFail(e);
	    }
	}
	
	@PatchMapping("/view/{aptSeq}")
	private ResponseEntity<?> addViewCount(@PathVariable String aptSeq, 
			@CookieValue(required = false) String recentViewed, 
			HttpServletResponse res,
			HttpSession session){
		// 조회수 중복 증가 방지를 위해 한 세션에서는 한 번씩만 조회수 count
		@SuppressWarnings("unchecked")
		Set<String> viewedSet = (Set<String>) session.getAttribute("viewedSet");
		if(viewedSet==null) {
			viewedSet = new HashSet<>();
			session.setAttribute("viewedSet", viewedSet);
		}
		cHandler.updateRecentViewedCookie(aptSeq, recentViewed, res);
		try {
			if(!viewedSet.contains(aptSeq)) {
				int viewCount = hService.modifyViewCount(aptSeq);
				viewedSet.add(aptSeq);
				return handleSuccess(Map.of("viewCount", viewCount));
			} else {
				return handleSuccess(Map.of("viewCount", hService.findViewCount(aptSeq)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@GetMapping("/view/starred/{aptSeq}")
	private ResponseEntity<?> IsStarred(@PathVariable String aptSeq,@AuthenticationPrincipal CustomUserDetails member){
		// 현재 아파트가 관심 아파트 매물인지 확인하는 메서드
		try {
		if(member==null) {
			return handleSuccess(Map.of("isStarred",""));
		}
		int memberId = member.getMember().getId();

		StarredDTO starred = StarredDTO.builder().userId(memberId).aptSeq(aptSeq).build();
		
		boolean isStarred = sService.isStarred(starred);
		
		return handleSuccess(Map.of("isStarred",isStarred));
		}catch(Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
		
	}
	
    // category 코드로 카테고리명 매핑
//	private String categoryMapping(String code) {
//		switch (code) {
//			case "FD6": return "음식점";
//			case "CE7": return "카페";
//			case "HP8": return "병원";
//			case "BK9": return "은행";
//			case "SC4": return "학교";
//			case "CS2": return "편의점";
//		}
//		return null;
//	}
}
