package com.ssafy.home.house.model.service;

import com.ssafy.home.house.model.dto.PropertyDTO;
import com.ssafy.home.house.model.dto.PropertySearchCondition;

import java.util.List;

public interface PropertyService {
    Long addProperty(PropertyDTO property);
    List<PropertyDTO> findPropertyList(PropertySearchCondition condition);
    int findPropertyCount(PropertySearchCondition condition);
    PropertyDTO findPropertyById(Long id);

    void modifyProperty(PropertyDTO property);
    void removeProperty(Long id);
}