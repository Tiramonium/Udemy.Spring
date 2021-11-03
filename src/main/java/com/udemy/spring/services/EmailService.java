package com.udemy.spring.services;

import javax.mail.internet.MimeMessage;
import com.udemy.spring.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmail(SimpleMailMessage message);
    void sendHtmlEmail(MimeMessage message);
    void sendOrderConfirmationEmail(Pedido pedido);
    void sendOrderConfirmationEmailHtml(Pedido pedido);
}
