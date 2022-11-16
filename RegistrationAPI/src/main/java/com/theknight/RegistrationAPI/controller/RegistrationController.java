package com.theknight.RegistrationAPI.controller;


import com.theknight.RegistrationAPI.entity.User;
import com.theknight.RegistrationAPI.entity.VerificationToken;
import com.theknight.RegistrationAPI.event.RegistrationCompleteEvent;
import com.theknight.RegistrationAPI.model.UserModel;
import com.theknight.RegistrationAPI.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @GetMapping("/hello")
    public String doHello(){
        return "Hello Kapil";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
        return "User Registered Successfully";
    }


    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.verifyRegistrationToken(token);
        if(result.equalsIgnoreCase("success")){
            return "User Verified Successfully";
        }
        else{
            return "User Verification Failed";
        }
//        return "hello";
    }

    @GetMapping("/resendVerifyToken")
    public String resentVerificationToken(@RequestParam("token") String existingToken,HttpServletRequest request){
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);

        User user = newToken.getUser();
        resentVerificationTokenMail(user,applicationUrl(request),newToken);
        return "Verification Token Resent Successfully";

    }

    private void resentVerificationTokenMail(User user, String applicationUrl,VerificationToken token) {

        String confirmationUrl = applicationUrl + "/verifyRegistration?token=" + token.getToken();
        log.info("Please click on the link below to verify your registration {}", confirmationUrl);

    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

    }
}
