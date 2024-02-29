package com.example.demo.Service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserDetailsInfoService implements org.springframework.security.core.userdetails.UserDetailsService {
  @Autowired
  private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDetailsInfoService() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepo.findByEmail(username);
        if (userInfo.isEmpty())
        {
            throw new UsernameNotFoundException("invalid username or password");

        }
        User user=userInfo.get();
        if(user.isBlocked())
        {
            throw new DisabledException("User is blocked");
        }
        return userInfo.map(com.example.demo.Service.UserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }

    public List<User> getAllUsers() {
        return userRepo.findALlByRolesContaining("ROLE_USER");
    }

    public void deleteUser(Long userId) {

            // Your logic to delete the user from the database
            // You can use userRepository.deleteById(userId) or any other appropriate method
            userRepo.deleteById(userId);

    }
    public List<String> getUserRoles(String username) {
        Optional<User> userOptional = userRepo.findByEmail(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return Collections.singletonList(user.getRoles());
        } else {
            return Collections.emptyList();
        }
    }

//user search
    public List<User> getUsersBySearch(String search) {
        return userRepo.findByUserNameContainingIgnoreCaseAndRolesContainingIgnoreCase(search, "ROLE_USER");
    }

    public boolean isEmailAlreadyRegistered(String email) {
        // Use the UserRepository to check if the email is already registered
        return userRepo.existsByEmail(email);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);

    }

    public boolean verifyOtpAndResetPassword(String otp, String newPassword) {
        User user=userRepo.findByOtp(otp);
        if(user!=null){
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setOtp(null);
            userRepo.save(user);
            return true;
        }
        else {

            return false;
        }
    }

    public void blockUser(long userId) {
     Optional<User>  optionalUser=userRepo.findById(userId);
     if(optionalUser.isPresent()){
         User user=optionalUser.get();
         user.setBlocked(true);
         userRepo.save(user);

     }
    }

    public void unblockUser(long userId) {
        Optional<User> optionalUser=userRepo.findById(userId);
        if(optionalUser.isPresent())
        {
            User user =optionalUser.get();
            user.setBlocked(false);
            userRepo.save(user);
        }
    }

    public List<User> getAllAdmins() {
        return userRepo.findALlByRolesContaining("ROLE_ADMIN");

    }

    public List<User> getAdminsBySearch(String search) {
        return userRepo.findByUserNameContainingIgnoreCaseAndRolesContainingIgnoreCase(search, "ROLE_ADMIN");
    }


    public Long getUserId(String userEmail) {
        Optional<User> optionalUser = userRepo.findByEmail(userEmail);

        return optionalUser.map(User::getId).orElse(null);
    }




    public String getUserName(String userEmail) {
        Optional<User> optionalUser = userRepo.findByEmail(userEmail);
        return optionalUser.map(User::getUserName).orElse(null);
    }

    public void updateUserProfile(User updatedUser) {
        User existingUser =userRepo.findById(updatedUser.getId()).orElse(null);
        if (existingUser != null) {
            // Update the user profile details
            existingUser.setUserName(updatedUser.getUserName());
            // Save the updated user profile to the database
            userRepo.save(existingUser);
        }
    }

    public void updateUserProfilePhone(User updatedUser) {
        User existingUser =userRepo.findById(updatedUser.getId()).orElse(null);
        if (existingUser != null) {
            // Update the user profile details
            existingUser.setMobile(updatedUser.getMobile());
            // Save the updated user profile to the database
            userRepo.save(existingUser);
        }
    }

    public void updateUserProfileAddress(User updatedUser) {
        User existingUser=userRepo.findById(updatedUser.getId()).orElse(null);
        if(existingUser!=null){
            existingUser.setAddress(updatedUser.getAddress());
            userRepo.save(existingUser);

        }
    }

    public void updateUserProfileDateOfBirth(User updatedUser) {


        User existingUser=userRepo.findById(updatedUser.getId()).orElse(null);
        if(existingUser!=null)
        {
            existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
            userRepo.save(existingUser);
        }
    }
}

