package com.practice.first.bookstore.user.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ForgotPasswordDTO {
    String emailId;
}
