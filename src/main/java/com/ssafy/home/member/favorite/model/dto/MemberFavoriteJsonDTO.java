package com.ssafy.home.member.favorite.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;



import lombok.Data;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberFavoriteJsonDTO {

    private String preferredLocation;

    private List<Long> budgetRange;
    private List<Long> rentRange;

    private String preferredType;

    private SpaceRequirements spaceRequirements;

    private String commuteTarget;
    private Integer commuteTimeLimit;

    private String householdType;
    private String floorPreference;
    private String buildYearPreference;

    private List<String> requiredFeatures;
    private List<String> lifestylePriority;

    @Data
    public static class SpaceRequirements {
        private Integer minArea;
        private Integer minRooms;
    }
}