package com.example.demo.controller.admincontroller;

import com.example.demo.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminImageController {
    @Autowired
    ImageService imageService;
    @PostMapping("hotel/image/{hotelId}")
    public ResponseEntity<String> uploadImageUrl(@PathVariable Long hotelId, @RequestBody String imageUrl){
         imageService.saveImage(imageUrl,hotelId);
         return new ResponseEntity<>(imageUrl,HttpStatus.OK);

    }

}
