package com.example.demo.repo;

import com.example.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Long> {
    List<Booking> findByCheckOutDateBeforeAndPaymentStatus(Date currentDate, String payAtHotel);

    List<Booking> findByUserId(Long userId);
}
