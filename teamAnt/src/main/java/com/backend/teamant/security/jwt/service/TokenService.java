package com.backend.teamant.security.jwt.service;

import lombok.RequiredArgsConstructor;
import com.backend.teamant.security.jwt.entity.Token;
import com.backend.teamant.security.jwt.repository.TokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    @Transactional
    public void saveToken(Token token) {
        tokenRepository.save(token);
    }

    public Token findByRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }

    public Token findByUserId(String userId) {
        return tokenRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteByUserId(String userId) {
         tokenRepository.deleteByUserId(userId);
    }

}
