package com.example.BKHotelSpring.exception;

public class UserAlReadyExistsException extends RuntimeException{
    public UserAlReadyExistsException(String message){
        super(message);
    }
}
