package com.practice.first.bookstore.user.util;

import com.practice.first.bookstore.user.entity.UserRegistration;
import com.practice.first.bookstore.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class ForgotPassword {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    EmailSender mailService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private static final int OTP_LENGTH = 6;
    private Map<String, String> emailIdOtp = new HashMap<>();
    public String otpGenerator(){
        Random random = new Random();
        int otp = random.nextInt((int) Math.pow(10, OTP_LENGTH));
        System.out.println("otp done");
        return String.valueOf(otp);
    }

    public void sendOTP(String emailId, String otp){
        String subject = "Reset Password";
        String body = "This is the otp for reset password " + otp + " Link: http://localhost:8080/users/forgotpassword";
        System.out.println("sent otp");
        mailService.sendEmail(emailId, subject, body);
        emailIdOtp.put(emailId,otp);
    }

    public void changePassword(String emailId, String otp, String newPassword){
        if(verifyOtp(emailId, otp)){
            UserRegistration userRegistration = userRepository.findByEmailId(emailId);
            String encodedPassword = passwordEncoder.encode(newPassword);
            if(userRegistration != null){
                userRegistration.setPassword(encodedPassword);
                userRepository.save(userRegistration);
                System.out.println("Password changed successfully");
            }
            else {
                System.out.println("Invalid Password");
            }
        }
    }

    public boolean verifyOtp(String emailId, String otp){
        String storedOtp = emailIdOtp.get(emailId);
        return storedOtp != null && storedOtp.equals(otp);
    }

}
