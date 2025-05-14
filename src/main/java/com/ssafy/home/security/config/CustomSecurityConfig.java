package com.ssafy.home.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafy.home.security.jwt.JwtAuthenticationFilter;
import com.ssafy.home.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {	
	
	private final JwtTokenProvider jwtTokenProvider;

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
    	http.cors(cors -> {})
    			.csrf(t->t.disable())
    			.authorizeHttpRequests(authorize -> authorize
    			.requestMatchers("/secured/admin/**").hasRole("ADMIN")
    			.requestMatchers("/secured/user/**").hasRole("USER")
    			.requestMatchers("/auth","/auth/**").authenticated()
    			.anyRequest().permitAll());
    	
    	// JWT 인증 필터 추가
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
	

    // AuthenticationManager 등록
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
