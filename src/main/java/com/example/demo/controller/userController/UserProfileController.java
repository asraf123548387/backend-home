package com.example.demo.controller.userController;

import com.example.demo.Service.UserDetailsInfoService;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserDetailsInfoService userDetailsInfoService;
@GetMapping("/profile/{userId}")
        public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
            User user = userRepo.findById(userId).orElse(null);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

@PutMapping("/profile/updateUser")
public ResponseEntity<?> updateUserProfile(@RequestBody User updatedUser)
{
    try{
        userDetailsInfoService.updateUserProfile(updatedUser);
        return ResponseEntity.ok("username updated successfully");

    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating userProfile");

    }
}
    @PutMapping("/profile/updatePhone")
    public ResponseEntity<?> updateUserProfilePhone(@RequestBody User updatedUser)
    {
        try{
            userDetailsInfoService.updateUserProfilePhone(updatedUser);
            return ResponseEntity.ok("username updated successfullyf");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating userProfile");

        }
    }

    @PutMapping("/profile/updateAddress")
    public ResponseEntity<?> updateUserProfileAddress(@RequestBody User updatedUser)
    {
        try {
            userDetailsInfoService.updateUserProfileAddress(updatedUser);
            return  ResponseEntity.ok("address updated successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating userProfile");
        }
    }
    @PutMapping("/profile/updateDateOfBirth")
    public ResponseEntity<?> updateUserProfileDateOfBirth(@RequestBody User updatedUser){

    try{
        userDetailsInfoService.updateUserProfileDateOfBirth(updatedUser);
        return ResponseEntity.ok("date of birth updated successfully");

    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating userProfile");
    }
    }


}
