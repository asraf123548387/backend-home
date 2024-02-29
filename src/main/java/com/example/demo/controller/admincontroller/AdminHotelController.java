package com.example.demo.controller.admincontroller;


import com.example.demo.Service.HotelService;
import com.example.demo.dto.HotelDTO;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.User;
import com.example.demo.repo.HotelRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminHotelController {
    @Autowired
    HotelService hotelService;
    @Autowired
    HotelRepo hotelRepo;
    @Autowired
    UserRepo userRepo;

    @GetMapping("/hotelList")
    public ResponseEntity<?> getAllHotels(@RequestParam(name = "search", required = false) String search) {
        try {
            List<Hotel> hotels;

            // Check if a search query is present
            if (search != null && !search.isEmpty()) {
                // If a search parameter is present, filter users based on the search query
                hotels = hotelService.getHotelBySearch(search);
            } else {
                // If no search parameter, fetch all users
                hotels = hotelService.getAllHotels();
            }
             System.out.println(hotels);
            // Return the list of users
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            // Handle any exceptions, e.g., database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users");
        }
    }

@PostMapping("/savehotel")
public ResponseEntity<String> saveHotel(@RequestBody HotelDTO hotelDTO){

   Long userId= hotelDTO.getUserId();
   User adminUser=userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found with id: " + userId));
   Hotel hotel=new Hotel();
   hotel.setHotelName(hotelDTO.getHotelName());
   hotel.setAddress(hotelDTO.getAddress());
   hotel.setPhone(hotelDTO.getPhone());
   hotel.setLocation(hotelDTO.getLocation());
   hotel.setEmail(hotelDTO.getEmail());
   hotel.setDescription(hotelDTO.getDescription());
    hotel.setImages(hotelDTO.getImages());
    hotel.setPrice(hotelDTO.getPrice());
    hotel.setAdminUser(adminUser);
    hotelRepo.save(hotel);
    return ResponseEntity.ok("Hotel added successfully");
}

@GetMapping("/hotel/{hotelId}")
    public ResponseEntity<Hotel> getHotelByIdDetails(@PathVariable Long hotelId){
        Hotel hotel=hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotel);
}
    @PutMapping("/hotel/{hotelId}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long hotelId, @RequestBody HotelDTO hotelDTO) {
        try {
            // Retrieve the existing hotel by ID
            Hotel existingHotel = hotelService.getHotelById(hotelId);

            // Check if the hotel with the given ID exists
            if (existingHotel == null) {
                return ResponseEntity.notFound().build();
            }

            // Update the hotel details with the values from the DTO
            existingHotel.setAddress(hotelDTO.getAddress());

            existingHotel.setDescription(hotelDTO.getDescription());
            existingHotel.setImages(hotelDTO.getImages());
            existingHotel.setEmail(hotelDTO.getEmail());
            existingHotel.setHotelName(hotelDTO.getHotelName());
            existingHotel.setPhone(hotelDTO.getPhone());
            existingHotel.setLocation(hotelDTO.getLocation());
            existingHotel.setPrice(hotelDTO.getPrice());

            // Save the updated hotel
            Hotel updatedHotel = hotelService.updateHotel(existingHotel);

            // Return the updated hotel in the response
            return ResponseEntity.ok(updatedHotel);
        } catch (Exception e) {
            // Handle exceptions or validation errors
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/countHotel/{userId}")
    public ResponseEntity<Long> countHotelsByUserId(@PathVariable Long userId) {
        Long count = hotelRepo. countByAdminUserId(userId);
        return ResponseEntity.ok(count);
    }



}
