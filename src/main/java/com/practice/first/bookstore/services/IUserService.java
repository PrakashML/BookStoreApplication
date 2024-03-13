package com.practice.first.bookstore.services;

import com.practice.first.bookstore.dto.LoginDTO;
import com.practice.first.bookstore.entity.UserRegistration;
import org.apache.catalina.User;

import java.util.List;

public interface IUserService {
    List<UserRegistration> getAllUsers();

    UserRegistration getUser(String token);

    UserRegistration createUser(UserRegistration user);

    UserRegistration updateUser(Long id, UserRegistration user);

    void deleteUser(Long id);
    String loginUser(LoginDTO loginDTO);
    UserRegistration verifyUser(String token);
}
