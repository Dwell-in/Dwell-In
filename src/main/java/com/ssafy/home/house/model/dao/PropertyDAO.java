package com.ssafy.home.house.model.dao;

import com.ssafy.home.house.model.dto.PropertyDTO;
import com.ssafy.home.house.model.dto.PropertySearchCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyDAO {
    int insertProperty(PropertyDTO property);
    int insertPropertyPrice(PropertyDTO property);
    int insertPropertyOption(Long propertyId, Long optionId);
    int insertPropertySafety(Long propertyId, Long safetyId);

    PropertyDTO selectPropertyById(Long id);
    List<PropertyDTO> selectProperties(PropertySearchCondition condition);
    int countProperties(PropertySearchCondition condition);

    int updateProperty(PropertyDTO property);
    int updatePropertyPrice(PropertyDTO property);
    int deleteProperty(Long id);
    int deletePropertyOptions(Long propertyId);
    int deletePropertySafeties(Long propertyId);

    List<String> selectOptionNamesByPropertyId(Long propertyId);
    List<String> selectSafetyNamesByPropertyId(Long propertyId);
}