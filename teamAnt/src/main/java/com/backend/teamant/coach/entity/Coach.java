package com.backend.teamant.coach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @SequenceGenerator(name = "id", initialValue = 1, allocationSize = 1)
    private Long id;
    private String coachName;
    private String coachId;
    private String auth;
    private String password;
    private String phoneNumber;

    public void update(String coachId, String coachName, String phoneNumber) {
        this.coachId = coachId;
        this.coachName = coachName;
        this.phoneNumber = phoneNumber;
    }
}
