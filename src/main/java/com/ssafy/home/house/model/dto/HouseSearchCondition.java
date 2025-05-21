package com.ssafy.home.house.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseSearchCondition {
    // 지번 주소 검색용
    private String sidoName;   // 시도 이름
    private String gugunName;  // 구군 이름
    private String dongName;   // 동 이름
    private String jibun;      // 지번

    // 도로명 주소 검색용
    private String roadNm;        // 도로명
    private String roadNmBonbun;  // 도로명 본번
    private String roadNmBubun;   // 도로명 부번

    // 아파트 이름
    private String aptNm;
}
