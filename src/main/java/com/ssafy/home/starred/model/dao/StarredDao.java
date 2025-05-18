package com.ssafy.home.starred.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.starred.model.dto.StarredDTO;
import com.ssafy.home.starred.model.dto.StarredHouseInfoDTO;

@Mapper
public interface StarredDao {

	boolean isStarred(StarredDTO starred);

	void insertStarred(StarredDTO starred);
	
	List<StarredHouseInfoDTO> selectStarred(StarredDTO starred);
	
	void deleteStarred(StarredDTO starred);

}
