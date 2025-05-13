package com.ssafy.home.security.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {
        String subject = "[Dwell-In] 이메일 인증 요청";
        String frontUrl = "http://localhost:8080";
        String verificationUrl = frontUrl+"/email/verify?token=" + token;

        String text = "다음 링크를 클릭하여 이메일 인증을 완료해주세요:\n\n" + verificationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
