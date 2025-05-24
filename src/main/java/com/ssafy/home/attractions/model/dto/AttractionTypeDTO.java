package com.ssafy.home.attractions.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttractionTypeDTO {
    String aptSeq;
    int type;
    double lat;
    double lon;
}
