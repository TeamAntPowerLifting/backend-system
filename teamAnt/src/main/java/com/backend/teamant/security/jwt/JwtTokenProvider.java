package com.backend.teamant.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.backend.teamant.security.jwt.response.JwtResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Token 생성 및 검증
 */
@Component
@PropertySource("classpath:config/jwt.properties")
public class JwtTokenProvider implements InitializingBean {

    private static String ACCESS = "access";
    private final String accessTokenKey;
    private final String refreshTokenKey;
    private final long accessTokenValidityInSeconds;
    private final long refreshTokenValidityInSeconds;
    private Key accessKey;
    private Key refreshKey;

    public JwtTokenProvider(@Value("${jwt.access-secret-key}") String accessTokenKey,
                            @Value("${jwt.refresh-secret-key}") String refreshTokenKey,
                            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds){
        this.accessTokenKey = accessTokenKey;
        this.refreshTokenKey = refreshTokenKey;
        this.accessTokenValidityInSeconds = tokenValidityInSeconds * 1000;
        this.refreshTokenValidityInSeconds = tokenValidityInSeconds * 2 * 1000 * 24 * 14;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 초기화 메소드 empty Object가 생성되고, DI 작업까지 마친 후 실행되는 메소드
        // Object 초기화 작업은 생성자에서 진행하지만 DI를 통해 빈이 주입된 후에 초기화할 작업이 있으면 사용.
        byte[] accessKeyByte = Decoders.BASE64.decode(accessTokenKey);
        byte[] refreshKeyByte = Decoders.BASE64.decode(refreshTokenKey);
        this.accessKey = Keys.hmacShaKeyFor(accessKeyByte);
        this.refreshKey = Keys.hmacShaKeyFor(refreshKeyByte);
    }

    public Authentication getAuthentication(String token, String type) {
        Claims claims = parseClaims(token, type);

        if(claims.get("auth") == null) {
            throw new RuntimeException("No Authorization. please check the token");
        }

        Collection<? extends GrantedAuthority> authorities= Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails principal = new User(claims.get("subject").toString(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String generateAccessToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + this.accessTokenValidityInSeconds);
        return Jwts.builder()
                .setClaims(claimsMap(authentication))
                .signWith(accessKey, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenExpiresIn)
                .compact();
    }

    public JwtResponse generateToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + this.accessTokenValidityInSeconds);
        Date refreshTokenExpiresIn = new Date(now + this.refreshTokenValidityInSeconds);

        String accessToken = Jwts.builder()
                .setClaims(claimsMap(authentication))
                .signWith(accessKey, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenExpiresIn)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claimsMap(authentication))
                .signWith(refreshKey, SignatureAlgorithm.HS512)
                .setExpiration(refreshTokenExpiresIn)
                .compact();

        return JwtResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenExpiration(accessTokenValidityInSeconds)
                .build();
    }

    public Map<String, Object> claimsMap(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(auth -> auth.getAuthority()).collect(Collectors.joining(","));

        Map<String, Object> claims = new HashMap<>();

        claims.put("issuer", "ANT");
        claims.put("auth", authorities);
        claims.put("subject", authentication.getName());
        return claims;
    }

    private Claims parseClaims(String token, String type) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(type.equals(ACCESS)? accessKey : refreshKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    public long getExpiration(String token, String type) {
        Date expiration = parseClaims(token, type).getExpiration();
        return expiration.getTime();
    }

    public String getUserId(String token, String type) {
        return parseClaims(token, type).getSubject();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, accessKey);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshKey);
    }

    public boolean validateToken(String token, Key key){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {

        } catch (ExpiredJwtException e) {

        } catch (UnsupportedJwtException e) {

        } catch (IllegalArgumentException e) {

        }
        return  false;
    }
}
