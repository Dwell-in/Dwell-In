package com.ssafy.home.member.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.home.member.model.dto.MemberDTO;

public interface MemberService {
	
    int addMember(MemberDTO emp);

    MemberDTO findMemberDetail(String email);
    
    MemberDTO findMemberById(int id);

    String findEmailByKakaoId(String id);
    
    int modifyMember(MemberDTO member);
    
    int removeMember(String email, String password);

    int removeMember(String email);
    
    void modifyRefreshToken(String email, String refreshToken);
    
    public String getRefreshToken(String email);
    
    public void removeRefreshToken(String email);
    
    List<Map<String, ?>> findChatTargetList(int id);
    
    List<MemberDTO> findAllMembers(int page, int size);
    List<MemberDTO> findMembersByState(String state);
    List<MemberDTO> findLoggedInMembers();
    void modifyMemberState(String email, String state);
    void modofyMemberRole(String email, String role);
}
