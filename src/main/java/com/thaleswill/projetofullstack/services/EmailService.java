package com.thaleswill.projetofullstack.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.thaleswill.projetofullstack.domain.Pedido;

public interface EmailService {
	
	//métodos de envio de email de confirmação de pedido em formato texto
	void emailDeConfirmacaoDePedido(Pedido obj);
	void sendEmail(SimpleMailMessage msg);

	//métodos de envio de email de confirmação de pedido em formato HTML
	void emailDeConfirmacaoDePedidoHtml(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
}
