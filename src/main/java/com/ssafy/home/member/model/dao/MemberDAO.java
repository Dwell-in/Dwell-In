package com.ssafy.home.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.home.member.model.dto.MemberDTO;


@Mapper
public interface MemberDAO {
	int insertMember(MemberDTO member);

	int updateMember(MemberDTO member);

	MemberDTO selectMemberByEmail(String email) ;

	MemberDTO selectMember(@Param("email") String email, @Param("password") String password);
	
	MemberDTO selectAllMember();

	String selectPassword(String email);
	
	int deleteMemberByEmail(String email);
	
	int deleteMember(@Param("email") String email, @Param("password") String password);
	
	String selectEmailByKakaoId(String id);
	
	List<String> selectChatTargetEmail(String loginUserEmail);
}
