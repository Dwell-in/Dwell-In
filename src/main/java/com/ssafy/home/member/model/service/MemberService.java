package com.ssafy.home.member.model.service;

import com.ssafy.home.member.model.dto.MemberDTO;

public interface MemberService {
	
    int addMember(MemberDTO emp);

    MemberDTO findMemberDetail(String email, String password);

    MemberDTO findMemberDetail(String email);

    int modifyMember(MemberDTO member);
    
    int removeMember(String email, String password);

    int removeMember(String email);
    
    String findEmailByKakaoId(String id);
}
