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
	private int id;					// ID
	private String aptSeq;			// PK
	private String aptNm;			// 아파트 이름
	private int buildYear;			// 건축년도
	private double lat;				// 위도
	private double lon;				// 경도
	private int viewCount;			// 매물 조회수
	
	// 특정 아파트에 대한 거래 내역
	private List<HouserdealDTO> listDeal;
}
