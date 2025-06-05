package com.ssafy.home.ai.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AptRecommendDTO {
    private String aptSeq;
    private String aptNm;
    private String roadNm;
    private String roadNmBonbun;
    private String roadNmBubun;
    private double lat;
    private double lng;
}
