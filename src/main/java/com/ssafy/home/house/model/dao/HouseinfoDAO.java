package com.ssafy.home.house.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.house.model.dto.HouseinfoDTO;
import com.ssafy.home.house.model.dto.HouserdealDTO;

@Mapper
public interface HouseinfoDAO {
	HouseinfoDTO selectInfo(String aptSeq);
	List<HouseinfoDTO> selectInfoList(String sggCd, String umdCd);
	int updateViewCount(String aptSeq);
	int selectViewCount(String aptSeq);
	List<HouseinfoDTO> selectInBounds(double swLat, double swLng,double neLat, double neLng);
	
	
}

