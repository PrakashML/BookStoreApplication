package com.practice.first.bookstore.dto;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String password;
    private String emailId;
}
