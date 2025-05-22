package com.ssafy.home.house.model.dto;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseinfoDTO {
	private String aptSeq;			// PK
	private String aptNm;			// 아파트 이름
	private String roadNm;			// 도로명
	private String roadNmBonbun;	// 
	private String roadNmBubun;		//
	private String jibun;
	private int buildYear;			// 건축년도
	private double lat;				// 위도
	private double lon;				// 경도
	private int viewCount;			// 매물 조회수
    private String sidoName;
    private String gugunName;
    private String dongName;
	// 특정 아파트에 대한 거래 내역
	private List<HouserdealDTO> listDeal;
}
