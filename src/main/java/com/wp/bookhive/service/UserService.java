package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.enums.Roles;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, Roles role);

}