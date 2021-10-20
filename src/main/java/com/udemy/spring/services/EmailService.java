package com.udemy.spring.services;

import com.udemy.spring.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmail(SimpleMailMessage mail);
    void sendOrderConfirmationEmail(Pedido pedido);
}
