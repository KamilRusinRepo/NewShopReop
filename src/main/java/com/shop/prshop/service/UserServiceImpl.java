package com.shop.prshop.service;

import com.shop.prshop.RandomString;
import com.shop.prshop.model.user.Role;
import com.shop.prshop.model.user.User;
import com.shop.prshop.repository.RoleRepository;
import com.shop.prshop.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private JavaMailSender javaMailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void saveUser(User user, String siteURL) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String verificationCode = RandomString.generate(64);
        user.setVerificationCode(verificationCode);
        user.setVerified(false);

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public void setVerificationEmail(User user, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        String emailAddress = user.getEmail();
        String fromAddress = "user.verification@wp.pl";
        String senderName = "Premium Reseler";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>" +
                "Click the link below to verify your registration:<br>" +
                "<h3><a href=\\[[url]]\\ target=\\_self\\>VERIFY</a></h3>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(emailAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = siteUrl + "/verify?code=" + user.getVerificationCode();
        content = content.replace("[[url]]", verifyURL);
        helper.setText(content, true);
        javaMailSender.send(message);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
