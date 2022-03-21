package com.wp.bookhive.service.impl;


import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.enums.Roles;
import com.wp.bookhive.models.exceptions.UserNotFoundException;
import com.wp.bookhive.repository.UserRepository;

import com.wp.bookhive.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Integer id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User register(String email, String password, String repeatPassword, Roles role) {
        // TODO custom exception
        if (email==null || email.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidParameterException();

        if (!password.equals(repeatPassword))
            //TODO: throw new PasswordsDoNotMatchException();
            throw new BadCredentialsException("Invalid Credentials");

        if(this.userRepository.findByEmail(email).isPresent())
            //TODO: throw new UsernameAlreadyExistsException(email);
            throw new BadCredentialsException("Invalid Credentials");

        User user = new User(email,passwordEncoder.encode(password), role);

        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
