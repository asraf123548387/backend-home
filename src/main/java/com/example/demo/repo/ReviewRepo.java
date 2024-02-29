package com.example.demo.repo;

import com.example.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review,Long> {


    List<Review> findByHotelHotelId(Long hotelId);

    List<Review> findByUserId(Long userId);
}
