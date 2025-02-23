package br.edu.ifpb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String destinatario, String assunto, String corpo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario); // Define o destinatário
        mensagem.setSubject(assunto); // Define o assunto do e-mail
        mensagem.setText(corpo);     // Define o corpo do e-mail

        mailSender.send(mensagem);  // Envia o e-mail
    }
}