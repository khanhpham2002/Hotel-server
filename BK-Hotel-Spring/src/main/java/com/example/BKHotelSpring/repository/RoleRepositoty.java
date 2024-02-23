package com.example.BKHotelSpring.repository;

import com.example.BKHotelSpring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepositoty extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    boolean existsByName(Role role);
}
