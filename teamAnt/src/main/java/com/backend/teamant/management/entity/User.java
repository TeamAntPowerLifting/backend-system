package com.backend.teamant.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @SequenceGenerator(name = "id", initialValue = 1, allocationSize = 1)
    private Long id;
    private Long CoachId;
    private String userName;
    private String age;
    private Date startDate;
    @OneToMany
    @JoinColumn(name = "id")
    private List<Schedule> schedule;
    private Date endDate;
    private String gender;
}
