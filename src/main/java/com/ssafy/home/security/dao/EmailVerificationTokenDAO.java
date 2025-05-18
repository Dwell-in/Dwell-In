package com.ssafy.home.security.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.home.security.dto.EmailVerificationTokenDTO;

@Mapper
public interface EmailVerificationTokenDAO {
    void insertToken(EmailVerificationTokenDTO token);

    EmailVerificationTokenDTO selectByToken(@Param("token") String token);

    void markTokenAsUsed(String token);
}
