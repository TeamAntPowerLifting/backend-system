package com.backend.teamant.coach.controller;

import com.backend.teamant.coach.request.CoachSaveRequest;
import com.backend.teamant.coach.request.CoachUpdateRequest;
import lombok.RequiredArgsConstructor;
import com.backend.teamant.coach.entity.Coach;
import com.backend.teamant.coach.response.CoachResponse;
import com.backend.teamant.coach.service.CoachService;
import com.backend.teamant.common.response.CommonResponse;
import com.backend.teamant.security.jwt.JwtTokenProvider;
import com.backend.teamant.security.jwt.entity.Token;
import com.backend.teamant.security.jwt.response.JwtResponse;
import com.backend.teamant.security.jwt.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class CoachController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenService tokenService;
    private final CoachService coachService;

    @PostMapping("/api/login")
    public CommonResponse<CoachResponse> login(@RequestBody Coach coach) {
        String coachId = coach.getCoachId();
        String password = coach.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(coachId, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); // 인증 객체 생성
        JwtResponse jwtResponse = jwtTokenProvider.generateToken(authentication); // 인증 객체 생성 후 token 발급
        String refreshToken = jwtResponse.getRefreshToken();

        tokenService.saveToken(new Token(coachId, refreshToken, new Date(jwtTokenProvider.getExpiration(refreshToken, "refreshToken"))));
        return CommonResponse.ok(CoachResponse.of(coach, jwtResponse));
    }

    @PostMapping("/api/logout")
    public CommonResponse<?> logout(@RequestBody Coach coach) {
        String coachId = coach.getCoachId();
        Token token = tokenService.findByUserId(coachId);
        if(token != null) {
            tokenService.deleteByUserId(coachId);
        }
        return CommonResponse.ok();
    }

    @GetMapping("/api/reissue-token")
    public CommonResponse<?> reissueToken(@RequestHeader String authorization) {
        String refreshToken = authorization.substring(7);
        if(jwtTokenProvider.validateRefreshToken(refreshToken)) {
            String accessToken = jwtTokenProvider.generateAccessToken(jwtTokenProvider.getAuthentication(refreshToken, "refreshToken"));
            JwtResponse response = JwtResponse.builder()
                    .grantType("Bearer")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenExpiration(jwtTokenProvider.getExpiration(accessToken, "access"))
                    .build();

            return CommonResponse.ok(response);
        } else {
            tokenService.deleteByUserId(jwtTokenProvider.getUserId(refreshToken, "refreshToken"));
            return CommonResponse.error("expiration token");
        }
    }


    @PostMapping("/save")
    public CommonResponse<CoachResponse> saveCoachInfo(@RequestBody CoachSaveRequest requestDto) {
        Coach coach = coachService.saveCoachInfo(requestDto);
        return CommonResponse.ok(CoachResponse.of(coach));
    }

    @DeleteMapping("/delete/{coachId}")
    public CommonResponse<?> deleteCoachInfo(@PathVariable Long coachId) {
        coachService.deleteCoachInfo(coachId);
        return CommonResponse.ok();
    }

    @PutMapping("/update/{id}")
    public CommonResponse<CoachResponse> updateCoachInfo(@PathVariable Long id, @RequestBody CoachUpdateRequest requestDto) {
        Coach coach = coachService.updateCoachInfo(id, requestDto);
        return CommonResponse.ok(CoachResponse.of(coach));
    }

    @GetMapping("/{coachId}")
    public CommonResponse<CoachResponse> getCoachInfo(@PathVariable Long coachId) {
        Coach coach = coachService.getCoachInfo(coachId);
        return CommonResponse.ok(CoachResponse.of(coach));
    }
    @GetMapping("/api/duplication")
    public CommonResponse<?> duplication(@RequestBody String coachId) {
        Boolean isExist = coachService.findByCoachId(coachId);
        if(Boolean.FALSE.equals(isExist)) {
            return CommonResponse.error("이미 등록된 아이디 입니다.");
        }
        return CommonResponse.ok();
    }
}
