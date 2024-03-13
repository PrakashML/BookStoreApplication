package com.practice.first.bookstore.services;

import com.practice.first.bookstore.dto.LoginDTO;
import com.practice.first.bookstore.entity.UserRegistration;
import com.practice.first.bookstore.repository.IUserRepository;
import com.practice.first.bookstore.util.EmailSender;
import com.practice.first.bookstore.util.Token;
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
    public UserRegistration createUser(UserRegistration user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public UserRegistration updateUser(Long id, UserRegistration updatedUser) {
        Optional<UserRegistration> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserRegistration existingUser = userOptional.get();
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setDob(updatedUser.getDob());
            existingUser.setUpdatedDate(updatedUser.getUpdatedDate());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setEmailId(updatedUser.getEmailId());
            existingUser.setVerify(updatedUser.getVerify());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public String loginUser(LoginDTO loginDTO) {
        UserRegistration userRegistration = userRepository.findByEmailIdAndPassword(loginDTO.getEmail(), loginDTO.getPassword());

        if(userRegistration != null){
            return t.createToken(userRegistration.getEmailId());
        }
        return null;
    }

    @Override
    public UserRegistration verifyUser(String token) {
        String user_id = t.decodeToken(token);
        UserRegistration user = userRepository.findByEmailId(user_id);
        return user;
    }

}
