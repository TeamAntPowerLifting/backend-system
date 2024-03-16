package com.backend.teamant.coach.service;

import lombok.RequiredArgsConstructor;
import com.backend.teamant.coach.entity.Coach;
import com.backend.teamant.coach.repository.CoachRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;

    public Coach getCoachInfo(String coachId) {
        return coachRepository.findByCoachId(coachId);
    }

    public Coach save(Coach coach) {
        return coachRepository.save(coach);
    }
}
