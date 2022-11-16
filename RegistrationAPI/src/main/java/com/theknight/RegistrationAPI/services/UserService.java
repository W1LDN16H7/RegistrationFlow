package com.theknight.RegistrationAPI.services;

import com.theknight.RegistrationAPI.entity.User;
import com.theknight.RegistrationAPI.entity.VerificationToken;
import com.theknight.RegistrationAPI.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String verifyRegistrationToken(String token);

    VerificationToken generateNewVerificationToken(String existingToken);
}
