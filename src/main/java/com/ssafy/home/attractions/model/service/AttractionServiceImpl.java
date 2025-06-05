package com.ssafy.home.attractions.model.service;

import com.ssafy.home.attractions.model.dao.AttractionDAO;
import com.ssafy.home.attractions.model.dto.AttractionResponseDTO;
import com.ssafy.home.attractions.model.dto.AttractionTypeDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService{

    private final AttractionDAO attractionDAO;

    @Override
    public List<AttractionResponseDTO> findAttraction(AttractionTypeDTO attractionTypeDTO) {
        return attractionDAO.selectAttraction(attractionTypeDTO);
    }
}
