package com.vta.app.components;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
public class LocalMailSender implements MailSender {
    @PostConstruct
    void init() {
        log.info("Loading local email service");
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        log.info("Sending Mail via local mail service..");
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        log.info("Sending mail via local mail service..");
    }
}
