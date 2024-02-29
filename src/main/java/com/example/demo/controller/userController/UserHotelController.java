package com.example.demo.controller.userController;

import com.example.demo.Service.HotelService;
import com.example.demo.Service.ImageService;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Image;
import com.example.demo.repo.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserHotelController {
    @Autowired
    public HotelService hotelService;
    @Autowired
    public HotelRepo hotelRepo;
    @Autowired
    public ImageService imageService;
    @GetMapping("/hotelList")
    public List<Hotel> getHotelListByUser(){
        return hotelService.getAllHotels();
    }


    @GetMapping("/hotelImages/{hotelId}")
    public ResponseEntity<List<String>> getHotelImages(@PathVariable Long hotelId){
        List<com.example.demo.entity.Image> images = imageService.getImagesByHotelId(hotelId);
        List<String> imageUrls = images.stream().map(Image::getImageUrl).collect(Collectors.toList());
        return new ResponseEntity<>(imageUrls, HttpStatus.OK);
    }

    @GetMapping("/hotelSingle/{hotelId}")
    public ResponseEntity<Hotel> getHotelDetails(@PathVariable Long hotelId)
    {
        return hotelRepo.findById(hotelId)
                .map(hotel -> ResponseEntity.ok(hotel)).
                orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/hotelListByLocation")
    public ResponseEntity<List<Hotel>> getHotelsByLocation(@RequestParam String location) {
        List<Hotel> hotels = hotelRepo.findByLocation(location);
        if (hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }




}
