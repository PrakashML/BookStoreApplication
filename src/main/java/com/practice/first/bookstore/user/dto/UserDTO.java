package com.practice.first.bookstore.user.dto;

import lombok.*;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
public class UserDTO {
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String password;
    private String emailId;
}
