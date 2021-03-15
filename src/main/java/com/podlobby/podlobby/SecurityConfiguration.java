package com.podlobby.podlobby;

import com.podlobby.podlobby.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsLoader userDetailsLoader;

    public SecurityConfiguration(UserDetailsLoader userDetailsLoader) {
        this.userDetailsLoader = userDetailsLoader;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsLoader).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // what urls you can visit
        http.formLogin()
                .loginPage("/login")//define the log in page ( spring security will handle post request to /login )
                .defaultSuccessUrl("/profile?myPodcasts") // define where you go after successful login
                .permitAll()//all people can visit the login page

                .and()
                .logout()
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login?logout")//once logged out they are sent to login page or wherever (?logout => extra message to then use to show something on page)

                .and()
                .authorizeRequests()
                .antMatchers("/", "/register", "/feeds/global")// pages you do not need to be logged in to view to the permitAll below ( can specify get and posts to the urls as well )
                .permitAll()

                .and() // pages need to be logged in to visit below
                .authorizeRequests()
                .antMatchers("/feeds/filtered", "/feeds/requests", "/followers/*", "/followers", "/home", "/podcasts/*", "/user-profile", "/recommendations", "/getCategories","/request", "/profile", "/otherProfile/*")//anything with post / something.. you need to be logged in to visit
                .authenticated();
    }
}