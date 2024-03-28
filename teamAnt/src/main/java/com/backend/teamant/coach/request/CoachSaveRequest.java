package com.backend.teamant.coach.request;

import com.backend.teamant.coach.entity.Coach;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoachSaveRequest {
    private String coachId;
    private String coachName;
    private String auth;
    private String password;
    private String phoneNumber;

    @Builder
    public CoachSaveRequest(String coachId, String coachName, String auth, String password, String phoneNumber) {
        this.coachId = coachId;
        this.coachName = coachName;
        this.auth = auth;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Coach toEntity() {
        return Coach.builder()
               .coachId(coachId)
               .coachName(coachName)
               .auth(auth)
               .password(password)
               .phoneNumber(phoneNumber)
               .build();
    }
}
