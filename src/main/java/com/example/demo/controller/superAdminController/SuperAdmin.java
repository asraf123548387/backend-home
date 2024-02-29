package com.example.demo.controller.superAdminController;

import com.example.demo.Service.UserDetailsInfoService;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/SAdmin")
public class SuperAdmin {
    @Autowired
    UserDetailsInfoService userDetailsInfoService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepo repo;
@GetMapping("/getAllAdmin")
public ResponseEntity<?> getAllUsers(@RequestParam(name = "search", required = false) String search) {
    try {
        List<User> users;

        // Check if a search query is present
        if (search != null && !search.isEmpty()) {
            // If a search parameter is present, filter users based on the search query
            users = userDetailsInfoService.getAdminsBySearch(search);
        } else {
            // If no search parameter, fetch all users
            users = userDetailsInfoService.getAllAdmins();
        }

        // Return the list of users
        return ResponseEntity.ok(users);
    } catch (Exception e) {
        // Handle any exceptions, e.g., database errors
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users");
    }
}
    @PostMapping("/addAdmin")
    public ResponseEntity<String> saveAddmin(@RequestBody User user){
    System.out.println(user);
        if (userDetailsInfoService.isEmailAlreadyRegistered(user.getEmail())) {
            return new ResponseEntity<>("Email is already registered", HttpStatus.BAD_REQUEST);
        }

        // Make sure the password is not null
        if (user.getPassword() == null) {
            return new ResponseEntity<>("Password cannot be null", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_ADMIN");
        user.setVerified(true);

        User savedUser = repo.save(user);

        return new ResponseEntity<>("User Saved", HttpStatus.OK);
    }
}
