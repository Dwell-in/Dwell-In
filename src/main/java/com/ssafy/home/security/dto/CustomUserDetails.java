package com.ssafy.home.security.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.home.member.model.dto.MemberDTO;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private @NonNull MemberDTO member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        if (member.getRole() != null) {
            roles.add(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        // P.K로 사용되는 정보
        return member.getEmail();
    }
}

