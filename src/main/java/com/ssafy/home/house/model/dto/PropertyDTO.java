// ✅ PropertyDTO
package com.ssafy.home.house.model.dto;

import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    private byte[] picture;
    @JsonProperty("propertyImg")
	public String getPropertyImg() {
		if (picture != null)
			return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(picture);
		else
			return "";
	}

    private List<Long> optionIds;
    private List<String> optionNames;

    private List<Long> safetyIds;
    private List<String> safetyNames;
}
