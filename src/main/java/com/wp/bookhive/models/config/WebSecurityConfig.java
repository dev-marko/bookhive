package com.wp.bookhive.models.config;

import com.wp.bookhive.models.config.oauth2.CustomOAuth2UserService;
import com.wp.bookhive.models.config.oauth2.OAuthLoginSuccessHandler;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CustomEmailPasswordAuthenticationProvider customEmailPasswordAuthenticationProvider;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuthLoginSuccessHandler oauthLoginSuccessHandler;
    private final DatabaseLoginSuccessHandler databaseLoginSuccessHandler;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/home","/register", "/**.jpg", "/**.png").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                    .formLogin().loginPage("/home")
//                    .defaultSuccessUrl("/user", true)
//                    .failureUrl("/login?error=BadCredentials")
//                .and()
//                    .logout().logoutSuccessUrl("/login?success=true")
//                    .clearAuthentication(true).invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID")
//                .and()
//                    .exceptionHandling().accessDeniedPage("/access-denied")
//                .and()
//                    .oauth2Login()
//                        .loginPage("/login")
//                        .userInfoEndpoint()
//                        .userService(oAuth2UserService)
//                .and()
//                        .defaultSuccessUrl("/user", true);
//
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/", "/login","/home","/register", "/**.jpg", "/**.png").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll()
//                .loginPage("/home")
//                    .defaultSuccessUrl("/user", true)
//                    .failureUrl("/home?error=BadCredentials")
//                    .successHandler(databaseLoginSuccessHandler)
//                .and()
//                .oauth2Login()
//                .loginPage("/home")
//                .userInfoEndpoint()
//                .userService(oAuth2UserService)
//                .and()
//                .successHandler(oauthLoginSuccessHandler)
//                .and()
//                .logout().logoutSuccessUrl("/login?success=true")
//                .clearAuthentication(true).invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .and()
//                .exceptionHandling().accessDeniedPage("/access-denied"); //"403"

                http.csrf().disable().authorizeRequests()
                .antMatchers("/home","/register", "/**.jpg", "/**.png", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/home")
                    .successHandler(databaseLoginSuccessHandler)
                    .defaultSuccessUrl("/user", true)
                    .failureUrl("/login?error=BadCredentials")
                .and()
                    .oauth2Login().loginPage("/home")
                    .userInfoEndpoint()
                    .userService(oAuth2UserService)
                    .and()
                    .successHandler(oauthLoginSuccessHandler)
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
