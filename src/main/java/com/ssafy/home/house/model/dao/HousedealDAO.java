package com.ssafy.home.house.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.house.model.dto.HouserdealDTO;

@Mapper
public interface HousedealDAO {
	List<HouserdealDTO> getListHouseDeal(String apt_seq);
}
