package com.shop.prshop.service;

import com.shop.prshop.model.user.CustomUserDetails;
import com.shop.prshop.model.user.User;
import com.shop.prshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException(email + "not found");
        }

        return new CustomUserDetails(user);
    }
}

