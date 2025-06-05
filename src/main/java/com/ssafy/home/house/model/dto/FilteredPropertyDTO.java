package com.ssafy.home.house.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilteredPropertyDTO {

    private String type;
    private int floor;
    private double supplyArea;
    private Long salePrice;
    private Long deposit;
    private Long monthlyRent;
    private String aptSeq;
    private List<String> optionNames;
    private List<String> safetyNames;
}
