package com.theknight.RegistrationAPI.services;

import com.theknight.RegistrationAPI.entity.User;
import com.theknight.RegistrationAPI.entity.VerificationToken;
import com.theknight.RegistrationAPI.model.UserModel;
import com.theknight.RegistrationAPI.repository.UserRepository;
import com.theknight.RegistrationAPI.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);

    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {

        VerificationToken verificationToken = new VerificationToken(user,token);
        verificationTokenRepository.save(verificationToken);




    }

    @Override
    public String verifyRegistrationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if(verificationToken == null){
            return "Invalid Token";
        }

        User user = verificationToken.getUser();

        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(verificationToken);
            return "Expired";
        }

        user.setActive(true);
        userRepository.save(user);
        return "success";




    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(existingToken);
        verificationToken.setToken(UUID.randomUUID().toString());

        verificationTokenRepository.save(verificationToken);
        return verificationToken;

    }


}
