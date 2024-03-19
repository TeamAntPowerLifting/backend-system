package com.backend.teamant.management.repository;

import com.backend.teamant.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
