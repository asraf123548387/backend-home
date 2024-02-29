package com.example.demo.Service;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.Image;
import com.example.demo.repo.HotelRepo;
import com.example.demo.repo.ImageRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    ImageRepo imageRepo;
    @Autowired
    HotelRepo hotelRepo;

    public void saveImage(String imageUrl, Long hotelId) {
        // Retrieve the Hotel entity from the database using the hotelId
        Optional<Hotel> hotelOptional = hotelRepo.findById(hotelId);

        if (hotelOptional.isPresent()) {
            // Hotel entity found, set it in the Image entity
            Hotel hotel = hotelOptional.get();
            Image image = new Image();
            image.setImageUrl(imageUrl.trim().replaceAll("\"", ""));
            // Set the complete image URL as provided
            image.setHotel(hotel); // Set the Hotel entity

            imageRepo.save(image);
        } else {
            // Handle case where Hotel entity with the provided ID is not found
            throw new EntityNotFoundException("Hotel with ID " + hotelId + " not found");
        }
    }


        public List<Image> getImagesByHotelId(Long hotelId) {
            return imageRepo.findByHotelHotelId(hotelId);
        }

    }



