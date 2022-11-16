package com.theknight.RegistrationAPI.event;

import com.theknight.RegistrationAPI.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private User user;

    public RegistrationCompleteEvent(User source,String appUrl) {
        super(source);
        this.user = source;
        this.appUrl = appUrl;

    }
}
