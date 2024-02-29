package com.example.demo.controller.userController;

import com.example.demo.Service.JwtService;
import com.example.demo.Service.UserDetailsInfoService;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.VerificationRequest;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


@RestController
@RequestMapping("/api")
public class UserController {

    // In this page contain all authentication details like otp and other things
    @Autowired
    UserDetailsInfoService userDetailsInfoService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepo repo;

// new user saving section
    @PostMapping("/saveUser")

    public ResponseEntity<String> saveUser(@RequestBody User user){
        if (userDetailsInfoService.isEmailAlreadyRegistered(user.getEmail())) {
            return new ResponseEntity<>("Email is already registered", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       user.setRoles("ROLE_USER");
       String otp=OtpGenerate();
       user.setOtp(otp);
        // Mark the user as unverified
        user.setVerified(false);
        sendOTPEmail(user.getEmail(), otp);


        User user2 = repo.save(user);

        return new ResponseEntity<String>("User Saved", HttpStatus.OK);
    }



    @PostMapping("/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerificationRequest request) {
        String email = request.getEmail();
        String otp = request.getOtp();

        // Find the user by email
        Optional<User> optionalUser = repo.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getOtp().equals(otp)) {

                user.setVerified(true);
                repo.save(user);

                return new ResponseEntity<>("Email verified successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect OTP.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
    }

    //Login section
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                String userEmail= authRequest.getEmail();
                List<String> roles = userDetailsInfoService.getUserRoles(authRequest.getEmail());
                Long userId=userDetailsInfoService.getUserId(userEmail);
                String userName=userDetailsInfoService.getUserName(userEmail);


                String token = jwtService.generateToken(authRequest.getEmail(),roles);
                System.out.println(userId);
                System.out.print(token);
                AuthResponse authResponse=new AuthResponse(token,userId,userName);
                return ResponseEntity.ok(authResponse);
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> processForgotPassword(@RequestBody String email) {
        try {
            String decodedEmail = URLDecoder.decode(email, "UTF-8");
            String emailWithoutEquals = decodedEmail.substring(0, decodedEmail.length() - 1);
            System.out.println(emailWithoutEquals);

            Optional<User> userOptional = userDetailsInfoService.getUserByEmail(emailWithoutEquals);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                String otp = OtpGenerate();
                user.setOtp(otp);
                repo.save(user);

                sendOTPEmail(user.getEmail(), otp);
                return ResponseEntity.ok("Password reset initiated. Check your email for further instructions.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for the provided email address.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }



    // otp generation sectrion
    private String OtpGenerate(){
        int otpLength=4;
        Random random=new Random();
        StringBuilder otp=new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
    private void sendOTPEmail(String email, String otp) {
        if (email == null || email.isEmpty()) {
            // Handle the case where the email address is missing or empty.
            // You can log an error or throw an exception.
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Thank you for sign up of HOME.com ");
        message.setText("Your OTP is: " + otp);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            // Handle email sending failure, e.g., log the error.
        }
    }


    @PostMapping("/verifyForgotPassword")
    public ResponseEntity<String> resetPassword(@RequestBody  Map<String, String> request){
        try{
            String otp=request.get("otp");
            String newPassword= request.get("newPassword");
            boolean isOtpValid=userDetailsInfoService.verifyOtpAndResetPassword(otp,newPassword);
            if(isOtpValid){
                return  ResponseEntity.ok("password reset successful");
            }
            else{
                return ResponseEntity.badRequest().body("invalid Otp");
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body("internal server erroe");
        }
    }


    
}



