package com.ssafy.home.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig {

	@Bean
	RoleHierarchy roleHierachy() {
		// default prefix는 ROLE_
		return RoleHierarchyImpl.withDefaultRolePrefix().role("ADMIN").implies("USER").role("USER").implies("GUEST")
				.build();
	}

	// 내부적으로 BCryptPasswordEncoder를 사용
	@Bean
	PasswordEncoder passEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain normalFileterChain(HttpSecurity http) throws Exception {
		// 임시로 수업때 사용한 경로 사용
		// 권한에 따라 접근할 수 있는 경로 지정해줘야 할 듯
    	http.authorizeHttpRequests(authorize -> authorize
    			.requestMatchers("/secured/admin/**").hasRole("ADMIN")
    			.requestMatchers("/secured/user/**").hasRole("USER")
    			.requestMatchers("/auth/**").authenticated()
    			.anyRequest().permitAll());
    	// csrf 토큰이 뭔지 아직 잘 몰라서 나중에 해보는 것도 좋을 것 같습니다
    	http.csrf(t -> t.disable());// csrf 생략
    	
    	http.formLogin(t -> t.loginPage("/member/login")
    			.loginProcessingUrl("/api/v1/member/login")
    			.usernameParameter("email")
    			.failureUrl("/member/login-form?error")
    			.permitAll() // 모든 사용자가 로그인 페이지에 접근할 수 있도록 하는 설정
    			);
    	
    	http.logout(t -> t.logoutUrl("/api/v1/member/logout")
    			.invalidateHttpSession(true)
    			.logoutSuccessUrl("/"));
//    			.deleteCookies("loginIdCooke")); // 나중에 쿠키 사용할 때 활성화
//    	로그인 폼에서 <input type="checkbox" name="remember-me"> 하면 로그인 유지하기 기능 사용할 수 있음
//    	http.rememberMe(t-> t.tokenValiditySeconds(60));
    	return http.build();
    }
}
