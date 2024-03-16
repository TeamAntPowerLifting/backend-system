package com.backend.teamant.coach.repository;

import com.backend.teamant.coach.entity.Coach;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach, String> {
    Coach findByCoachId(String coachId);

    @Transactional
    Coach save(Coach coach);
}
