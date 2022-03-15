package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.enums.Roles;
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
    public User register(String email, String password, String repeatPassword, Roles role) {
        // TODO custom exception
        if (email==null || email.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidParameterException();

        if (!password.equals(repeatPassword))
            //throw new PasswordsDoNotMatchException();
            throw new BadCredentialsException("Invalid Credentials");

        if(this.userRepository.findByEmail(email).isPresent())
            //throw new UsernameAlreadyExistsException(email);
            throw new BadCredentialsException("Invalid Credentials");

        User user = new User(email,passwordEncoder.encode(password), role);

        return this.userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        // TODO custom exception
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidParameterException();
        }

        return userRepository.findByEmailAndPassword(email, password).orElseThrow(InvalidParameterException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
