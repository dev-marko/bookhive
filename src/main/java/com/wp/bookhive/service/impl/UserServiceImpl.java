package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.config.oauth2.CustomOAuth2User;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.enums.AuthenticationType;
import com.wp.bookhive.models.enums.Roles;
import com.wp.bookhive.models.exceptions.UserNotFoundException;
import com.wp.bookhive.repository.UserRepository;
import com.wp.bookhive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(Integer id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User register(String name, String surname, String email, String password, String repeatPassword) {
        // TODO custom exception
        if (email==null || email.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidParameterException();

        if (!password.equals(repeatPassword))
            //TODO: throw new PasswordsDoNotMatchException();
            throw new BadCredentialsException("Invalid Credentials");

        if(this.userRepository.findByEmail(email).isPresent())
            //TODO: throw new UsernameAlreadyExistsException(email);
            throw new BadCredentialsException("Invalid Credentials");

        User user = new User(name, surname, email, passwordEncoder.encode(password));
        user.setAuthType(AuthenticationType.DATABASE);
        user.setRole(Roles.ROLE_USER);

        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userRepository.findByEmail(customOAuth2User.getEmail()).orElseThrow(() -> new UserNotFoundException(customOAuth2User.getEmail()));
        } else {
            user = (User) auth.getPrincipal();
        }
        return user;
    }


}
