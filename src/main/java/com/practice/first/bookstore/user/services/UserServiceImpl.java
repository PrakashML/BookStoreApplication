package com.practice.first.bookstore.user.services;

import com.practice.first.bookstore.user.dto.LoginDTO;
import com.practice.first.bookstore.user.entity.UserRegistration;
import com.practice.first.bookstore.user.repository.IUserRepository;
import com.practice.first.bookstore.user.util.EmailSender;
import com.practice.first.bookstore.user.util.ForgotPassword;
import com.practice.first.bookstore.user.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private Token t;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EmailSender mailService;
    @Autowired
    private ForgotPassword forgot;

    @Override
    public List<UserRegistration> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public UserRegistration getUser(String token) {
        String email = t.decodeToken(token);
        UserRegistration userRegistration = userRepository.findByEmailId(email);
        Optional<UserRegistration> userOptional = userRepository.findById(userRegistration.getId());
        return userOptional.orElse(null);
    }

    @Override
    public String createUser(UserRegistration user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setVerify(true);
        userRepository.save(user);
        String body = "Registered successfully. " + "verify your account " + "http://localhost:8080/login";
        String subject = "Verify Your Account";
        System.out.println(user.getEmailId());
        mailService.sendEmail(user.getEmailId(),subject,body);
        return "User registered successfully";
    }

//    @Override
//    public UserRegistration updateUser(Long id, UserRegistration updatedUser) {
//        Optional<UserRegistration> userOptional = userRepository.findById(id);
//        if (userOptional.isPresent()) {
//            UserRegistration existingUser = userOptional.get();
//            existingUser.setFirstName(updatedUser.getFirstName());
//            existingUser.setLastName(updatedUser.getLastName());
//            existingUser.setDob(updatedUser.getDob());
//            existingUser.setUpdatedDate(updatedUser.getUpdatedDate());
//            existingUser.setPassword(updatedUser.getPassword());
//            existingUser.setEmailId(updatedUser.getEmailId());
//            existingUser.setVerify(updatedUser.getVerify());
//            return userRepository.save(existingUser);
//        }
//        return null;
//    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public String loginUser(LoginDTO loginDTO) {
        UserRegistration userRegistration = userRepository.findByEmailId(loginDTO.getEmail());
        System.out.println(userRegistration);
        if(userRegistration != null && passwordEncoder.matches(loginDTO.getPassword(),userRegistration.getPassword())){
            String token =  t.createToken(userRegistration.getEmailId());
            System.out.println(loginDTO.getPassword());
            String body = "Registered Successfully";
            String subject = "account verified";
            mailService.sendEmail(userRegistration.getEmailId(), subject, body);
            userRepository.save(userRegistration);
            return "Login successful. Token: " + token;
        }
        else{
            return "Login failed.";
        }
    }

    @Override
    public String forgotPassword(String emailId) {
        String otp = forgot.otpGenerator();
        System.out.println("forgot 1 done");
        forgot.sendOTP(emailId,otp);
        System.out.println("forgot 2 done");
        return "OTP sent Successfully";
    }

    @Override
    public String changePassword(String emailId, String otp, String newPassword) {
        forgot.changePassword(emailId,otp,newPassword);
        return "Password changed Successfully";
    }

    @Override
    public UserRegistration verifyUser(String token) {
        String user_id = t.decodeToken(token);
        UserRegistration user = userRepository.findByEmailId(user_id);
        return user;
    }

    @Override
    public UserRegistration findByEmail(String EmailId) {
        return userRepository.findByEmailId(EmailId);
    }


}
