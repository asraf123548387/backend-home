package com.example.demo.controller.userController;

import com.example.demo.Service.WishListService;
import com.example.demo.dto.WishListRequest;
import com.example.demo.dto.WishlistDto;
import com.example.demo.entity.WishList;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class UserWishlistController {
    @Autowired
    WishListService wishListService;

    @PostMapping("/add")
    public ResponseEntity<WishList> addingWishlist(@RequestBody WishListRequest wishListRequest) {
        try {
            Long userId = wishListRequest.getUserId();
            Long hotelId = wishListRequest.getHotelId();
            WishList wishlist = wishListService.addToWishlist(userId, hotelId);

            // Return the appropriate response
            return ResponseEntity.ok(wishlist);
        } catch (BadRequestException e) {
            // Return a bad request response with the error message
            return ResponseEntity.badRequest().body(null);
        }





    }


    @GetMapping("/list/{userId}")
    public ResponseEntity<List<WishlistDto>> getWishlistByUserId(@PathVariable Long userId) {

            // Fetch the wishlist from the service and convert it to DTOs
            List<WishlistDto> wishlist = wishListService.getWishlistByUserId(userId);
            return ResponseEntity.ok(wishlist);


    }




}
