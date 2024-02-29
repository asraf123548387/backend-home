package com.example.demo.Service;

import com.example.demo.dto.ReviewDto;
import com.example.demo.dto.ReviewWithUserNameDto;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.repo.HotelRepo;
import com.example.demo.repo.ReviewRepo;
import com.example.demo.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    ReviewRepo reviewRepo;
    @Autowired
    HotelRepo hotelRepo;
    @Autowired
    UserRepo userRepo;
    public void addReview(ReviewDto reviewDto) {
        Hotel hotel = hotelRepo.findByHotelId(reviewDto.getHotelId());

        User user=userRepo.findById(reviewDto.getUserId()) .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + reviewDto.getUserId()));

        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setDescription(reviewDto.getDescription());
        review.setRating(reviewDto.getRating());
        review.setHotel(hotel);
        review.setUser(user);
        review.setReviewDate(new Date());

        // Save the Review entity
        reviewRepo.save(review);


    }

    public List<ReviewWithUserNameDto> getReviewsHotelById(Long hotelId) {
        return reviewRepo.findByHotelHotelId(hotelId).stream()
                .map(review -> {
                    ReviewWithUserNameDto reviewDto= new ReviewWithUserNameDto();
                    reviewDto.setReviewId(review.getReviewId());
                    reviewDto.setTitle(review.getTitle());
                    reviewDto.setReviewDate(review.getReviewDate());
                    reviewDto.setDescription(review.getDescription());
                    reviewDto.setRating(review.getRating());
                    reviewDto.setUserName(review.getUser().getUserName());
                    System.out.println(reviewDto.getUserName());

                    return reviewDto;


                }).collect(Collectors.toList());
    }

    public List<ReviewDto> getUserReviewsByUserId(Long userId) {
        List<Review> reviews=reviewRepo.findByUserId(userId);
        List<ReviewDto> reviewDTOS=reviews.stream().map(review -> {
            ReviewDto reviewDto=new ReviewDto();
            reviewDto.setRating(review.getRating());
            reviewDto.setDescription(review.getDescription());
            reviewDto.setTitle(review.getTitle());
            String hotelName=review.getHotel().getHotelName();
            reviewDto.setHotelName(hotelName);
            String image=review.getHotel().getImages();
            reviewDto.setImage(image);
            String place=review.getHotel().getLocation();
            reviewDto.setPlace(place);

            return reviewDto;



        }) .collect(Collectors.toList());

  return reviewDTOS;
    }




}
