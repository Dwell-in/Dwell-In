package com.ssafy.home.starred.model.dto;

import java.util.List;

import com.ssafy.home.house.model.dto.HouserdealDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarredHouseInfoDTO {
	private String aptSeq;			// PK
	private String aptNm;			// 아파트 이름
	private int buildYear;			// 건축년도
	private double lat;				// 위도
	private double lon;				// 경도
	private String sidoName;		// 시 이름
	private String gugunName;		// 구 이름
	private String dongName;		// 동 이름
	private String roadNm;
	private String roadNmBonbun;
	private int viewCount;			// 매물 조회수
	
}
