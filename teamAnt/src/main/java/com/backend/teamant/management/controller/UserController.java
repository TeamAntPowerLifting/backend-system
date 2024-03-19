package com.backend.teamant.management.controller;


import com.backend.teamant.common.response.CommonResponse;
import com.backend.teamant.management.entity.User;
import com.backend.teamant.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/api/user")
    public CommonResponse<?> save(@RequestBody User user) {
        User userInfo = userService.save(user);
        return CommonResponse.ok(userInfo.getId());
    }

    @GetMapping("/api/user/{userId}")
    public CommonResponse<?> findById(@RequestBody Long userId) {
        User user = userService.findById(userId);
        return CommonResponse.ok(user);
    }

    @DeleteMapping("/api/user/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        userService.deleteByUserId(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api/user")
    public ResponseEntity<?> update(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
