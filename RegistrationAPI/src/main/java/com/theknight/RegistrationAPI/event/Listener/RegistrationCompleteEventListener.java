package com.theknight.RegistrationAPI.event.Listener;

import com.theknight.RegistrationAPI.entity.User;
import com.theknight.RegistrationAPI.event.RegistrationCompleteEvent;
import com.theknight.RegistrationAPI.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        User user = event.getUser();
        String appUrl = event.getAppUrl();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token,user);

        String url = event.getAppUrl() + "/verifyRegistration?token=" + token;
        log.info("Click the link to verify your account " + url);




    }

}
