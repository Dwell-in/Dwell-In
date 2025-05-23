package com.ssafy.home.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.home.member.model.dto.MemberDTO;


@Mapper
public interface MemberDAO {
	int insertMember(MemberDTO member);

	int updateMember(MemberDTO member);

	MemberDTO selectMemberById(int id) ;

	MemberDTO selectMemberByEmail(String email) ;

	MemberDTO selectAllMember();

	String selectPassword(String email);
	
	int deleteMemberByEmail(String email);
	
	int deleteMember(@Param("email") String email, @Param("password") String password);
	
	String selectEmailByKakaoId(String id);
	
	List<Integer> selectChatTargetId(int id);
	
    String selectRefreshTokenByEmail(String email);

    void updateRefreshTokenByEmail(@Param("email") String email, @Param("refreshToken") String refreshToken);

    void deleteRefreshTokenByEmail(String email);

}
