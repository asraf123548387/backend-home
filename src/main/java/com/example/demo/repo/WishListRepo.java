package com.example.demo.repo;

import com.example.demo.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishListRepo extends JpaRepository<WishList,Long> {
    Optional<WishList> findByUserIdAndHotelHotelId(Long userId, Long hotelId);

    List<WishList> findByUserId(Long userId);
}
