package com.ssafy.home.house.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertySearchCondition {

    // 기본 검색 조건
    private String aptName;             // 아파트 이름 (LIKE)
    private String type;                // 매매 / 전세 / 월세
    private String direction;           // 방향 (남, 북 등)
    private Boolean parkingAvailable;   // 주차 가능 여부
    private Boolean elevator;           // 엘리베이터 여부
    private Boolean immediateMoveIn;    // 즉시 입주 가능 여부
    private String structureType;       // 복도식 / 계단식

    // 면적 조건
    private Double minNetArea;
    private Double maxNetArea;

    // 층 조건
    private Integer minFloor;
    private Integer maxFloor;

    // 방/욕실 수
    private Integer minRooms;
    private Integer minBathrooms;

    // 가격 조건
    private Long minPrice;
    private Long maxPrice;
    
    private List<Long> optionIds = new ArrayList<>();
    private List<Long> safetyIds = new ArrayList<>();

    // 페이징
    private int page = 1;
    private int size = 10;

    public int getOffset() {
        return (page - 1) * size;
    }
}
