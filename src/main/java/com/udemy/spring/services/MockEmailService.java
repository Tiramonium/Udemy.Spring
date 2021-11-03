package com.udemy.spring.services;

import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {
    private Logger logger = LogManager.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage message) {
        logger.info("Simulando envio de e-mail");
        logger.info(message.toString());
        logger.info("E-mail enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage message) {
        logger.info("Simulando envio de e-mail HTML");
        logger.info(message.toString());
        logger.info("E-mail enviado");
    }
}
