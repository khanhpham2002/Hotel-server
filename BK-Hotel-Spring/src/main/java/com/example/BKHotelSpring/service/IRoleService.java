package com.example.BKHotelSpring.service;

import com.example.BKHotelSpring.model.Role;
import com.example.BKHotelSpring.model.User;

import java.util.List;

public interface IRoleService {
    List<Role> getRoles();

    Role createRole(Role theRole);

    void deleteRole(Long id);

    Role findRoleByName(String name);

    User removeUserFromRole(Long UserId, Long roleId);

    User assignRoleToUser(Long userId, Long roleId);

    Role removeAllUsersFromRole(Long roleId);
}
