package com.udemy.spring.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {
    private Logger logger = LogManager.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage mail) {
        logger.info("Simulando envio de e-mail");
        logger.info(mail.toString());
        logger.info("E-mail enviado");
    }
}
