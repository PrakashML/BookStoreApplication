package com.practice.first.bookstore.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "registered_date")
    private LocalDate registeredDate = LocalDate.now();

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "password")
    private String password;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "verify")
    private Boolean verify=false;

    @Column(name = "otp")
    private int otp;
    public UserRegistration(String firstName, String lastName, LocalDate dob, String password, String emailId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.password = password;
        this.emailId = emailId;
        this.verify = false;
    }

}
