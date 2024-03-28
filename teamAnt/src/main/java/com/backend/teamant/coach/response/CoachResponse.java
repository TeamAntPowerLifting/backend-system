package com.backend.teamant.coach.response;

import lombok.Builder;
import lombok.Getter;
import com.backend.teamant.coach.entity.Coach;
import com.backend.teamant.security.jwt.response.JwtResponse;

// response - dto
@Getter
@Builder
public class CoachResponse {
    private String coachId;
    private String password;
    private String auth;
    private JwtResponse token;

    public static CoachResponse of(Coach coach, JwtResponse jwtResponse) {
        return CoachResponse.builder()
                .coachId(coach.getCoachId())
                .password(coach.getPassword())
                .auth(coach.getAuth())
                .token(jwtResponse)
                .build();
    }

    public static CoachResponse of(Coach coach) {
        return CoachResponse.builder()
                .coachId(coach.getCoachId())
                .password(coach.getPassword())
                .auth(coach.getAuth())
                .build();
    }
}
