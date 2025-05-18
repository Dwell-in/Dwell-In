package com.ssafy.home.house.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.house.model.dao.PropertyDAO;
import com.ssafy.home.house.model.dto.PropertyDTO;
import com.ssafy.home.house.model.dto.PropertySearchCondition;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyDAO dao;

    @Override
    @Transactional
    public Long addProperty(PropertyDTO dto) {
        dao.insertProperty(dto);
        dao.insertPropertyPrice(dto);
        if (dto.getOptionIds() != null) {
            for (Long id : dto.getOptionIds()) {
                dao.insertPropertyOption(dto.getId(), id);
            }
        }
        if (dto.getSafetyIds() != null) {
            for (Long id : dto.getSafetyIds()) {
                dao.insertPropertySafety(dto.getId(), id);
            }
        }
        return dto.getId();
    }

    @Override
    public List<PropertyDTO> findPropertyList(PropertySearchCondition condition) {
        return dao.selectProperties(condition);
    }

    @Override
    public int findPropertyCount(PropertySearchCondition condition) {
        return dao.countProperties(condition);
    }

    @Override
    public PropertyDTO findPropertyById(Long id) {
        return dao.selectPropertyById(id);
    }

    @Override
    @Transactional
    public void modifyProperty(PropertyDTO dto) {
        dao.updateProperty(dto);
        dao.updatePropertyPrice(dto);
        dao.deletePropertyOptions(dto.getId());
        dao.deletePropertySafeties(dto.getId());
        if (dto.getOptionIds() != null) {
            for (Long id : dto.getOptionIds()) {
                dao.insertPropertyOption(dto.getId(), id);
            }
        }
        if (dto.getSafetyIds() != null) {
            for (Long id : dto.getSafetyIds()) {
                dao.insertPropertySafety(dto.getId(), id);
            }
        }
    }

    @Override
    @Transactional
    public void removeProperty(Long id) {
        dao.deletePropertyOptions(id);
        dao.deletePropertySafeties(id);
        dao.deleteProperty(id);
    }
}