package com.ssafy.home.attractions.controller;

import com.ssafy.home.attractions.model.dto.AttractionResponseDTO;
import com.ssafy.home.attractions.model.dto.AttractionTypeDTO;
import com.ssafy.home.attractions.model.service.AttractionService;
import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.house.model.dto.HouseinfoDTO;
import com.ssafy.home.house.model.service.HouseService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attraction")
@RequiredArgsConstructor
public class AttractionController implements RestControllerHelper {
    private final AttractionService attractionService;
    private final HouseService houseService;

    @PostMapping("/attraction-list")
    public ResponseEntity<?> attractionList(@RequestBody AttractionTypeDTO attractionTypeDTO){

        String aptSeq = attractionTypeDTO.getAptSeq();
        System.out.println(aptSeq);
        HouseinfoDTO houseinfoDTO = houseService.findInfo(aptSeq);
        attractionTypeDTO.setLat(houseinfoDTO.getLat());
        attractionTypeDTO.setLon(houseinfoDTO.getLon());
        List<AttractionResponseDTO> attarctionList = attractionService.findAttraction(attractionTypeDTO);

        return handleSuccess(Map.of(
                "data",attarctionList
        ));

    }
}
