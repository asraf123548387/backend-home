package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor

public class HotelDTO {
    private String address;
    private Long userId;
    private String description;
    private String images;
    private String email;
    private String hotelName;
    private String phone;
    private String location;
    private String price;

}
