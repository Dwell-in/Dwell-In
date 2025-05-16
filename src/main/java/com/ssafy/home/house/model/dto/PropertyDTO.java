// ✅ PropertyDTO
package com.ssafy.home.house.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {
    private Long id;
    private String aptSeq;
    private Long memberId;

    private String aptName;      
    private String memberName;   

    private String title;
    private String type;
    private Double netArea;
    private Double supplyArea;
    private Integer floor;
    private Integer totalFloor;
    private Integer rooms;
    private Integer bathrooms;
    private String direction;
    private Boolean elevator;
    private Boolean parkingAvailable;
    private Boolean immediateMoveIn;
    private String structureType;
    private String description;
    private Integer viewCount;
    private String registeredAt;

    private Long salePrice;
    private Long deposit;
    private Long monthlyRent;
    private Integer managementFee;

    private List<Long> optionIds;
    private List<String> optionNames;

    private List<Long> safetyIds;
    private List<String> safetyNames;
}
