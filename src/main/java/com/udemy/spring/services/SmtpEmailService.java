package com.udemy.spring.services;

import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {
    @Autowired
    private MailSender sender;

    @Autowired
    private JavaMailSender mailSender;
    private Logger logger = LogManager.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage message) {
        logger.info("Enviando e-mail");
        sender.send(message);
        logger.info("E-mail enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage message) {
        logger.info("Enviando e-mail");
        mailSender.send(message);
        logger.info("E-mail enviado");
    }
}
