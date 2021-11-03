package com.udemy.spring.services;

import java.util.Date;
import javax.mail.internet.MimeMessage;
import com.udemy.spring.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public abstract class AbstractEmailService implements EmailService {
    @Value("${default.mail.sender}")
    private String sender;

    @Autowired
    private TemplateEngine engine;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendOrderConfirmationEmail(Pedido pedido) {
        SimpleMailMessage message = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(message);
    }

    @Override
    public void sendOrderConfirmationEmailHtml(Pedido pedido) {
        MimeMessage message = prepareMimeMessageFromPedido(pedido);
        sendHtmlEmail(message);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(pedido.cliente.email);
        message.setFrom(this.sender);
        message.setReplyTo(this.sender);
        message.setSubject("Pedido confirmado! Código: " + pedido.id);
        message.setSentDate(new Date(System.currentTimeMillis()));
        message.setText(pedido.toString());
        return message;
    }

    protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) {
        Context context = new Context();
        context.setVariable("pedido", pedido);
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try
        {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.addTo(pedido.cliente.email, pedido.cliente.nome);
            helper.setFrom(this.sender);
            helper.setReplyTo(this.sender);
            helper.setSubject("Pedido confirmado! Código: " + pedido.id);
            helper.setSentDate(new Date(System.currentTimeMillis()));
            helper.setText(pedido.toString(), engine.process("email/confirmacaoPedido", context));
            return helper.getMimeMessage();
        }
        catch (Exception exception)
        {
            sendOrderConfirmationEmail(pedido);
        }

        return null;
    }
}
