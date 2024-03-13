package com.shop.prshop.service;

import com.shop.prshop.model.user.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    void saveUser(User user, String siteUrl);
    List<User> findAllUsers();
    void setVerificationEmail(User user, String siteUrl) throws MessagingException, UnsupportedEncodingException;
}
