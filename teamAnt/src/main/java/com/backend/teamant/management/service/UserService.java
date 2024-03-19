package com.backend.teamant.management.service;

import com.backend.teamant.management.entity.User;
import com.backend.teamant.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

    public void deleteByUserId(Long userId){
        userRepository.deleteById(userId);
    }
}
