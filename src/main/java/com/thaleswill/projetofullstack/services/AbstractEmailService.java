package com.thaleswill.projetofullstack.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.thaleswill.projetofullstack.domain.Cliente;
import com.thaleswill.projetofullstack.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	// preparação de email de confirmação de pedido usando o padrão Template Method

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngene;

	@Autowired
	private JavaMailSender javaMailSender;

	//envia um email de confirmação de pedido para o cliente formato texto.
	@Override
	public void emailDeConfirmacaoDePedido(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);

	}

	//Prepara um email com o novo pedido realizado pelo cliente formato texto
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	//envia o email com a nova senha solicitada pelo cliente
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);
	}
	
	//Prepara um email com a nova senha solicitada pelo cliente
	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}

	//envia um email de confirmação de pedido para o cliente formato HTML.
	@Override
	public void emailDeConfirmacaoDePedidoHtml(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			emailDeConfirmacaoDePedido(obj);
		}
	}

	// injeta "obj" com o apelido de "pedido" no template e retorna o HTML como String
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngene.process("email/confirmacaoPedido", context);
	}

	//Prepara um email com o novo pedido realizado pelo cliente formato HTML
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}
}
