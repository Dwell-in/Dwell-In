package com.ssafy.home.member.favorite.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.member.favorite.model.dto.MemberFavoriteDTO;

@Mapper
public interface MemberFavoriteDAO {

	void insertMemberFavorite(MemberFavoriteDTO dto);
}
