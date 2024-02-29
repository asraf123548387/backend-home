package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "wishlist")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;
    @JsonBackReference("user-wishlist")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JsonBackReference("hotel-wishlist")
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

}
