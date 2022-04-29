package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.config.oauth2.CustomOAuth2User;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.enums.AuthenticationType;
import com.wp.bookhive.models.enums.Roles;
import com.wp.bookhive.models.exceptions.*;
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

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.Objects;
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
    @Transactional
    public User update(User user, String name, String surname, Integer age, String address, String email, String password, String confirmPassword) {
        if (user==null)
            return null;

        if(name != null && !name.equals("")) {
            user.setName(name);
        } else {
            throw new NameNotValidException(name);
        }

        if(surname != null && !surname.equals("")) {
            user.setSurname(surname);
        } else {
            throw new SurnameNotValidException(surname);
        }

        if(age != null && age>0) {
            user.setAge(age);
        } else {
            return null;
        }

        if(user.getAuthType().equals(AuthenticationType.DATABASE) && email != null && email.contains("@") && email.length() > 1) {
            //if (this.userRepository.findAll().stream()
                    //.noneMatch(u -> !Objects.equals(u.getId(), user.getId()) && user.getEmail().equals(email))) {
                user.setEmail(email);
//            } else {
//                throw new EmailAlreadyExistsException(email);
//            }
        }
//        else {
//            throw new EmailNotValidException(email);
//        }

        user.setAddress(address);

        if (user.getAuthType().equals(AuthenticationType.DATABASE) && !confirmPassword.equals("") && !password.equals("") && password.equals(confirmPassword))
            user.setPassword(passwordEncoder.encode(password));


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
