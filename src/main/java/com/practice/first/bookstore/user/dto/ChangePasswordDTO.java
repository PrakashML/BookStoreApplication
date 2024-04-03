package com.practice.first.bookstore.user.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    String email;
    String otp;
    String newPassword;
}
