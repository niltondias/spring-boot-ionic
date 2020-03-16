package br.com.finework.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.finework.cursomc.domain.Pedido;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj );
    void sendEmail( SimpleMailMessage msg );

    void sendOrderConfirmationHtmlEmail(Pedido obj);
    void sendHtmlEmail(MimeMessage msg);

}