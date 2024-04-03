package com.practice.first.bookstore.user.controller;

import com.practice.first.bookstore.user.dto.ChangePasswordDTO;
import com.practice.first.bookstore.user.dto.ForgotPasswordDTO;
import com.practice.first.bookstore.user.dto.LoginDTO;
import com.practice.first.bookstore.user.entity.UserRegistration;
import com.practice.first.bookstore.user.services.UserServiceImpl;
import com.practice.first.bookstore.user.util.EmailSender;
import com.practice.first.bookstore.user.util.ForgotPassword;
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
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private ForgotPassword forgotPassword;

    @GetMapping
    public List<UserRegistration> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserRegistration> getUser(@RequestHeader String token) {
        UserRegistration user = userService.getUser(token);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> createUser(@RequestBody UserRegistration userRegistration) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRegistration));
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<UserRegistration> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
//        UserRegistration updatedUser = userService.updateUser(id, new UserRegistration(
//                userDTO.getFirstName(),
//                userDTO.getLastName(),
//                userDTO.getDob(),
//                userDTO.getPassword(),
//                userDTO.getEmailId(),
//        ));
//        return ResponseEntity.ok(updatedUser);
//    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordDTO input){
        return userService.forgotPassword(input.getEmailId());
    }

    @PostMapping("/change-password")
    String changePassword(@RequestBody ChangePasswordDTO input){
        return userService.changePassword(input.getEmail(),input.getOtp(),input.getNewPassword());
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
