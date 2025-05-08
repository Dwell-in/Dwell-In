package com.ssafy.home.search.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.search.model.dto.DongDTO;

@Mapper
public interface DongDAO {
	public String selectCode(DongDTO dongDTO);
	public List<String> selectSidoList();
	public List<String> selectGugunList(String sido);
	public List<String> selectDongList(String sido, String gugun);
}
