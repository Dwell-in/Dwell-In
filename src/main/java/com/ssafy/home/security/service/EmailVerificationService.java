package com.ssafy.home.security.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ssafy.home.security.dao.EmailVerificationTokenDAO;
import com.ssafy.home.security.dto.EmailVerificationTokenDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationTokenDAO tokenDao;

    public String createVerificationToken(String email) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(30);

        EmailVerificationTokenDTO tokenObj = EmailVerificationTokenDTO.builder()
                .email(email)
                .token(token)
                .expiryDate(expiry)
                .isUsed(false)
                .build();

        tokenDao.insertToken(tokenObj);
        return token;
    }
    
    public EmailVerificationTokenDTO findbyToken(String token) {
    	return tokenDao.selectByToken(token);
    }
    
    public void markTokenAsUsed(String token) {
    	tokenDao.markTokenAsUsed(token);
    }
}
