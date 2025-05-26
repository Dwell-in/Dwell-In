package com.ssafy.home.attractions.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttractionResponseDTO {
    String title;
    String areaCode;
    String content;
    String imgSrc;
    double lat;
    double lng;
    String addr1;
    String addr2;
    String tel;
    String overview;
    String homePage;

}
