package com.wp.bookhive.models.config.oauth2;

import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.enums.AuthenticationType;
import com.wp.bookhive.models.enums.Roles;
import com.wp.bookhive.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2User, clientName);

        Optional<User> userOptional = userRepository.findByEmail(customOAuth2User.getEmail());
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setEmail(customOAuth2User.getEmail());
            user.setName(customOAuth2User.getName().trim().split("\\s+")[0]);
            user.setSurname(customOAuth2User.getName().trim().split("\\s+")[1]);
            user.setAuthType(AuthenticationType.GOOGLE);
            user.setRole(Roles.ROLE_USER);
            userRepository.save(user);
        }
        return customOAuth2User;
    }

}