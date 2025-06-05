package com.ssafy.home.attractions.model.dao;

import com.ssafy.home.attractions.model.dto.AttractionResponseDTO;
import com.ssafy.home.attractions.model.dto.AttractionTypeDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionDAO {
    List<AttractionResponseDTO> selectAttraction(AttractionTypeDTO attractionTypeDTO);
}
