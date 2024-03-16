package com.backend.teamant.security.jwt.repository;

import com.backend.teamant.security.jwt.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Token findByRefreshToken(String refreshToken);
    Token findByUserId(String userId);
    void deleteByUserId(String userId);
}
