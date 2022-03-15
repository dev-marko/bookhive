package com.wp.bookhive.models.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CustomEmailPasswordAuthenticationProvider customEmailPasswordAuthenticationProvider;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, CustomEmailPasswordAuthenticationProvider customEmailPasswordAuthenticationProvider) {
        this.passwordEncoder = passwordEncoder;
        this.customEmailPasswordAuthenticationProvider = customEmailPasswordAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Privremeno dodeka testirame

//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/")
//                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customEmailPasswordAuthenticationProvider);
    }
}
