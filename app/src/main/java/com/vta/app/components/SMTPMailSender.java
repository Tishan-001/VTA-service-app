package com.vta.app.components;

import com.vta.app.enums.GlobalConfigs;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
@Profile("prod")
public class SMTPMailSender extends JavaMailSenderImpl {

    public SMTPMailSender(ContextConfigs contextConfigs) {
        this.setHost(contextConfigs.getConfig(GlobalConfigs.EMAIL_HOST));
        this.setUsername(contextConfigs.getConfig(GlobalConfigs.EMAIL_USERNAME));
        this.setPassword(contextConfigs.getConfig(GlobalConfigs.EMAIL_PASSWORD));
        this.setPort(contextConfigs.getConfigAsInt(GlobalConfigs.EMAIL_PORT.getConfigName(), 587));

        Properties props = this.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", contextConfigs.getConfigAsString("mail.debug", "true"));
    }

    @PostConstruct
    void init() {
        log.info("Loading gmail service");
    }
}