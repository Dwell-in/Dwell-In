package com.ssafy.home.search.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DongDTO {
	private String sido;  // 시도 이름
	private String gugun; // 구군 이름
	private String dong; // 동 이름
}
