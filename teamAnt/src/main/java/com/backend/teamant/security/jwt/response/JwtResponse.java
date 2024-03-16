package com.backend.teamant.security.jwt.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private long tokenExpiration;
}
