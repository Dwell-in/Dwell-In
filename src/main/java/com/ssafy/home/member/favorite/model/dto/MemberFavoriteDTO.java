package com.ssafy.home.member.favorite.model.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberFavoriteDTO {
	 private Long memberId;

	    private String preferredLocation;
	    private Long budgetMin;
	    private Long budgetMax;
	    private Long rentMin;
	    private Long rentMax;
	    private String preferredType;
	    private int minArea;
	    private int minRooms;
	    private String commuteTarget;
	    private int commuteTimeLimit;
	    private String householdType;
	    private String floorPreference;
	    private String buildYearPreference;

	    private String requiredFeatures;
	    private String lifestylePriority;
}
