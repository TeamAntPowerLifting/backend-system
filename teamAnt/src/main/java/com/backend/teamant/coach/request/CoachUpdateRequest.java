package com.backend.teamant.coach.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoachUpdateRequest {
    private String coachId;
    private String coachName;
    private String phoneNumber;

    @Builder
    public CoachUpdateRequest(String coachId, String coachName, String phoneNumber) {
        this.coachId = coachId;
        this.coachName = coachName;
        this.phoneNumber = phoneNumber;
    }
}
