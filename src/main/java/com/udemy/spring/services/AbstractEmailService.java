package com.udemy.spring.services;

import java.util.Date;
import com.udemy.spring.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {
    @Value("${default.mail.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Pedido pedido) {
        SimpleMailMessage message = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(message);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(pedido.cliente.email);
        message.setFrom(this.sender);
        message.setSubject("Pedido confirmado! Código: " + pedido.id);
        message.setSentDate(new Date(System.currentTimeMillis()));
        message.setText(pedido.toString());
        return message;
    }
}
