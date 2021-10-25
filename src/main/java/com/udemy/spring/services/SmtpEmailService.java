package com.udemy.spring.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {
    @Autowired
    private MailSender mailSender;
    private Logger logger = LogManager.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage mail) {
        logger.info("Enviando e-mail");
        mailSender.send(mail);
        logger.info("E-mail enviado");
    }
}
