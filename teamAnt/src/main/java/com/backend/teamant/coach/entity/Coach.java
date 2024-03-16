package com.backend.teamant.coach.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
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
}
