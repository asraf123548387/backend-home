package com.example.demo.Service;

import com.example.demo.dto.WishlistDto;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.User;
import com.example.demo.entity.WishList;
import com.example.demo.repo.HotelRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.WishListRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishListService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    HotelRepo hotelRepo;
    @Autowired
    WishListRepo wishListRepo;

    public WishList addToWishlist(Long userId, Long hotelId) throws BadRequestException {
        User user = userRepo.findById(userId).orElseThrow(() -> new BadRequestException("User not found"));
        Hotel hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new BadRequestException("Hotel not found"));
       Long userId1=user.getId();
       Long hotelId1=hotel.getHotelId();

        Optional<WishList> existingWishlist = wishListRepo.findByUserIdAndHotelHotelId(userId1, hotelId1);

        // If the hotel is not in the wishlist, create a new Wishlist entity
        if (!existingWishlist.isPresent()) {
            WishList wishlist = new WishList();
            wishlist.setUser(user);
            wishlist.setHotel(hotel);
            return wishListRepo.save(wishlist);
        } else {
            // If the hotel is already in the wishlist, throw an exception
            throw new BadRequestException("Hotel is already in the wishlist");
        }
    }

    public List<WishlistDto> getWishlistByUserId(Long userId) {

       List<WishList> wishLists=wishListRepo.findByUserId(userId);
       List<WishlistDto> wishlistDTOs=wishLists.stream().map(wishList -> {
           WishlistDto wishlistDto=new WishlistDto();
           String hotelName=wishList.getHotel().getHotelName();
           wishlistDto.setHotelName(hotelName);
           String image=wishList.getHotel().getImages();
           wishlistDto.setImage(image);
           String place=wishList.getHotel().getLocation();
           wishlistDto.setPlace(place);
           String price=wishList.getHotel().getPrice();
           wishlistDto.setPrice(price);
           double rating=wishList.getHotel().getRating();
           wishlistDto.setRating(String.valueOf(rating));
           return wishlistDto;

       }).collect(Collectors.toList());
       return wishlistDTOs;
    }
}

