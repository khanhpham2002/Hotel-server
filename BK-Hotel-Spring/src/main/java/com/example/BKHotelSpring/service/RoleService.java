package com.example.BKHotelSpring.service;

import com.example.BKHotelSpring.exception.RoleAlreadyExistException;
import com.example.BKHotelSpring.exception.UserAlReadyExistsException;
import com.example.BKHotelSpring.model.Role;
import com.example.BKHotelSpring.repository.RoleRepositoty;
import com.example.BKHotelSpring.model.User;
import com.example.BKHotelSpring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{

    private final RoleRepositoty roleRepositoty;
    private final UserRepository userRepository;
    @Override
    public List<Role> getRoles() {
        return roleRepositoty.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_" + theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if(roleRepositoty.existsByName(role)){
            throw new RoleAlreadyExistException(theRole.getName()+" role already exists");
        }
        return roleRepositoty.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepositoty.deleteById(roleId);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepositoty.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepositoty.findById(roleId);
        if(role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepositoty.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepositoty.findById(roleId);
        if(user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlReadyExistsException(user.get().getFirstName()+ "is already assigned to the "+ role.get().getName() + " role");

        }
        if(role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepositoty.save(role.get());
        }
        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepositoty.findById(roleId);
        role.get().removeAllUsersFromRole();
        return roleRepositoty.save(role.get());
    }
}
