package com.backend.teamant.security.config;

import lombok.RequiredArgsConstructor;
import com.backend.teamant.security.jwt.JwtTokenProvider;
import com.backend.teamant.security.jwt.config.JwtSecurityConfig;
import com.backend.teamant.security.jwt.handler.JwtAccessDeniedHandler;
import com.backend.teamant.security.jwt.handler.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // 생성자 자동 생성
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final String[] AUTH_WHITELIST = {
            "/"
            , "/api/reissue-token"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling((handler) -> handler.accessDeniedHandler(jwtAccessDeniedHandler).authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((request) -> request.requestMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated())
                .apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**", "/assets/**", "/favicon.ico");
    }

}
