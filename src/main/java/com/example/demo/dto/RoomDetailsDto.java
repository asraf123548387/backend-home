package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsDto {
    private String images;
    private String pricePerNight;
    private String roomNumber;
    private String roomType;
    private Long hotelId;
    private Long roomId;
}
