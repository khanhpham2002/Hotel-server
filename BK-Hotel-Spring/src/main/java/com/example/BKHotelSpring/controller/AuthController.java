package com.example.BKHotelSpring.controller;

import com.example.BKHotelSpring.exception.UserAlReadyExistsException;
import com.example.BKHotelSpring.model.User;
import com.example.BKHotelSpring.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> regiterUser(User user){
        try{
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful!");
        }catch (UserAlReadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
