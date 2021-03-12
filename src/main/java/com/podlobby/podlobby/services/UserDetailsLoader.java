package com.podlobby.podlobby.services;

import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.model.UserWithRoles;
import com.podlobby.podlobby.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader implements UserDetailsService {

    private final UserRepository userDao;

    public UserDetailsLoader(UserRepository userDao){
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("find a user with UN : " + username);
        User user = userDao.findByUsername(username);
        System.out.println("----before null check, is there a user? ----");
        if(user == null){
            throw new UsernameNotFoundException("No user found with username " + username);
        }
        System.out.println(user.getUsername());
        return new UserWithRoles(user); // the enhanced UserDetails copy user
    }
}
