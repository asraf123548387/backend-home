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
public class BookingDto {
    private Long booking_id;
    private String guestName;
    private String email;
    private String mobileNumber;
    private Date checkInDate;
    private Date checkOutDate;
    private Double totalPrice;
    private Long roomId;
    private Long userId;
    private String hotelName;
    private String paymentStatus;
    private String address;
    private String roomNumber;
    private String roomType;
}
