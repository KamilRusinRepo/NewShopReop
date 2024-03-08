package com.shop.prshop.service;

import com.shop.prshop.model.user.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    User findUserByEmail(String email);
    List<User> findAllUsers();
}
