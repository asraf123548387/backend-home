package com.example.demo.controller.userController;

import com.example.demo.Service.BookingService;
import com.example.demo.Service.NotificationService;
import com.example.demo.dto.BookingDto;
import com.example.demo.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserBookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private NotificationService notificationService;


    @PostMapping("/roomBooking")
    public ResponseEntity<String> handleRoomBooking(@RequestBody BookingDto bookingDto){
        try {
            bookingService.bookRoom(bookingDto);
            return new ResponseEntity<>("booking successfully", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Booking failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/onlineBooking")
    public ResponseEntity<String> handleRoomOnlineBooking(@RequestBody BookingDto bookingDto) {
        try {
            // Assuming bookingService.onlineBookRoom returns the created booking
            BookingDto createdBooking = bookingService.onlineBookRoom(bookingDto);
            // Send a notification about the new booking
            notificationService.sendNotification("New booking: " + createdBooking.toString());
            return new ResponseEntity<>("Booking successfully", HttpStatus.OK);
        } catch (Exception e) {
            // Log the error and return an appropriate response
            return new ResponseEntity<>("Booking failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/userBookingList/{userId}")
    public ResponseEntity<List<BookingDto>> getUserBookings(@PathVariable Long userId){
        List<BookingDto> bookings=bookingService.getUserBookings(userId);

        return ResponseEntity.ok(bookings);
    }
}
