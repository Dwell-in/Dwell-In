package com.ssafy.home.attractions.model.service;

import com.ssafy.home.attractions.model.dto.AttractionResponseDTO;
import com.ssafy.home.attractions.model.dto.AttractionTypeDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AttractionService {
    List<AttractionResponseDTO> findAttraction(AttractionTypeDTO attractionTypeDTO);
}
