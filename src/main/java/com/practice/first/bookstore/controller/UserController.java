package com.practice.first.bookstore.controller;

import com.practice.first.bookstore.dto.LoginDTO;
import com.practice.first.bookstore.dto.UserDTO;
import com.practice.first.bookstore.entity.UserRegistration;
import com.practice.first.bookstore.services.UserServiceImpl;
import com.practice.first.bookstore.util.Token;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public List<UserRegistration> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserRegistration> getUser(@RequestHeader String token) {
        UserRegistration user = userService.getUser(token);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserRegistration> createUser(@RequestBody UserDTO userDTO) {
        UserRegistration newUser = userService.createUser(new UserRegistration(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getDob(),
                userDTO.getPassword(),
                userDTO.getEmailId()
        ));
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRegistration> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserRegistration updatedUser = userService.updateUser(id, new UserRegistration(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getDob(),
                userDTO.getPassword(),
                userDTO.getEmailId()
        ));
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/login")
    public String loginUser(@RequestBody LoginDTO loginDTO){
        return userService.loginUser(loginDTO);
    }

    @GetMapping("/verify")
    public ResponseEntity<UserRegistration> verifyUser(@RequestHeader String token){
        UserRegistration response = userService.verifyUser(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
