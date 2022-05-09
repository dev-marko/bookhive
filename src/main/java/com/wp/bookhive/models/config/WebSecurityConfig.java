package com.wp.bookhive.models.config;

import com.wp.bookhive.models.config.oauth2.CustomOAuth2UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomEmailPasswordAuthenticationProvider customEmailPasswordAuthenticationProvider;
    private final CustomOAuth2UserService oAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable().authorizeRequests()
                .antMatchers("/home","/register", "/**.jpg", "/**.png", "/**.css", "/oauth2/**", "/books/**", "/authors/**", "/bookshops/all", "/bookshops/search").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/home")
                    .defaultSuccessUrl("/user", true)
                    .failureUrl("/login?error=BadCredentials")
                .and()
                    .oauth2Login().loginPage("/home")
                    .userInfoEndpoint()
                    .userService(oAuth2UserService)
                    .and()
                    .defaultSuccessUrl("/user", true)
                .and()
                    .logout().logoutSuccessUrl("/login?success=true")
                    .clearAuthentication(true).invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customEmailPasswordAuthenticationProvider);
    }
}
