package com.ssafy.home.starred.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.starred.model.dto.StarredDTO;

@Mapper
public interface StarredDao {

	boolean isStarred(StarredDTO starred);

	void insertStarred(StarredDTO starred);
	
	void deleteStarred(StarredDTO starred);

}
