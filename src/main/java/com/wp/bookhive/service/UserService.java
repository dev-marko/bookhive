package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findById(Integer id);
    User findByEmail(String email);
    User register(String name, String surname, String username, String password, String repeatPassword);
    User getAuthenticatedUser();
    User update(User user, String name, String surname, Integer age, String address, String email, String password, String confirmPassword);
}
