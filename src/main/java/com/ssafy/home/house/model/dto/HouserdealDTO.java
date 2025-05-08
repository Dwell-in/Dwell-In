package com.ssafy.home.house.model.dto;

import java.util.Date;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouserdealDTO {
	private @NonNull String aptSeq;	// PK
	private @NonNull int dealYear; 	// 거래년도
	private @NonNull int dealMonth; // 거래월
	private @NonNull int dealDay; 	// 거래일
	private Date date;				// 거래 년도,월,일을 이용해서 Date 타입으로 저장
	private @NonNull double exclu; 	// 아파트면적
	private @NonNull String amount; 	// 거래가격
}
