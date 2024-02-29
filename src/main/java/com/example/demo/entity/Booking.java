package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long booking_id;
    private String guestName;
    private String email;
    private String phone;
    private Date checkInDate;
    private Date checkOutDate;
    private Double totalPrice;
    private String paymentStatus;

    @JsonBackReference("user-bookings")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JsonBackReference("room-bookings")
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
