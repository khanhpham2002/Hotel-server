package com.example.BKHotelSpring.service;

import com.example.BKHotelSpring.model.Role;
import com.example.BKHotelSpring.repository.RoleRepositoty;
import com.example.BKHotelSpring.model.User;
import com.example.BKHotelSpring.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepositoty roleRepositoty;
    @Override
    public User registerUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UsernameNotFoundException(user.getEmail() + "already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepositoty.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email);
        if(theUser != null) {
            userRepository.deleteUserByEmail(email);
        }
    }

    @Override
    public User getUser(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
