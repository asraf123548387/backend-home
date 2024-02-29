package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewWithUserNameDto {
    private Long reviewId;
    private String title;
    private String description;
    private int rating;
    private Date reviewDate;
    private String userName;


}
