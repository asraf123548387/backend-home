package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String title;
    private String description;
    private int rating;
    private Long hotelId;
    private Long userId;
    private String hotelName;
    private String image;
    private String place;


}
