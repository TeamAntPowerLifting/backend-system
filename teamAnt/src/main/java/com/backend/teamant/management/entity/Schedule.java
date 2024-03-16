package com.backend.teamant.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Schedule {

    @Id
    @GeneratedValue
    private Long Id;
    private Long userId;
    private String memo;
    private Date date;
}
