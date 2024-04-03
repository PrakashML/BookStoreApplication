package com.practice.first.bookstore.user.services;

import com.practice.first.bookstore.user.dto.LoginDTO;
import com.practice.first.bookstore.user.entity.UserRegistration;

import java.util.List;

public interface IUserService {
    List<UserRegistration> getAllUsers();

    UserRegistration getUser(String token);

    String createUser(UserRegistration user);

//    UserRegistration updateUser(Long id, UserRegistration user);

    void deleteUser(Long id);

    String loginUser(LoginDTO loginDTO);

    UserRegistration verifyUser(String token);

    UserRegistration findByEmail(String EmailId);

    String forgotPassword(String emailId);
    String changePassword(String emailId, String otp, String newPassword);
}
