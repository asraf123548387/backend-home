package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class  Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String roomNumber;
    private double pricePerNight;
    private boolean availability;
    @ManyToOne
    @JsonBackReference("hotel-rooms")
    @JoinColumn(name="hotelId")
    private Hotel hotel;

    private String images;
    private String roomType;
    @JsonManagedReference("room-bookings")
    @OneToMany(mappedBy = "room",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
