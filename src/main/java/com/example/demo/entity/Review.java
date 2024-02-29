package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String title;
    @Column(length = 2000)
    private String description;
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("hotel-reviews")
    @JoinColumn(name = "hotel_id",nullable = false)
    private Hotel hotel;

    @JsonBackReference("user-reviews")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Date reviewDate;



}
