package com.backend.teamant.coach.service;

import com.backend.teamant.coach.request.CoachSaveRequest;
import com.backend.teamant.coach.request.CoachUpdateRequest;
import lombok.RequiredArgsConstructor;
import com.backend.teamant.coach.entity.Coach;
import com.backend.teamant.coach.repository.CoachRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;

    public Coach getCoachInfo(Long id) {
        return coachRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않는 coachId 입니다. id=" + id));
    }

    @Transactional
    public Coach saveCoachInfo(CoachSaveRequest requestDto) {
        return coachRepository.save(requestDto.toEntity());
    }

    public void deleteCoachInfo(Long id) {
        coachRepository.deleteById(id);
    }

    @Transactional
    public Coach updateCoachInfo(Long id, CoachUpdateRequest requestDto) {
        Coach coach = coachRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않는 coachId 입니다. id=" + id));
        coach.update(requestDto.getCoachId(), requestDto.getCoachName(), requestDto.getPhoneNumber());
        return coach;
    }

    public Boolean findByCoachId(String coachId) {
        return coachRepository.findByCoachId(coachId) != null;
    }
}
