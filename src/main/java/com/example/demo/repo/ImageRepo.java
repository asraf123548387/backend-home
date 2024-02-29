package com.example.demo.repo;

import com.example.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ImageRepo extends JpaRepository<Image,Long> {


    List<Image> findByHotelHotelId(Long hotelId);
}
