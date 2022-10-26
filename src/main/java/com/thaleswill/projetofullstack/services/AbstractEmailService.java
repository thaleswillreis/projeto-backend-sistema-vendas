package com.thaleswill.projetofullstack.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.thaleswill.projetofullstack.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	//preparação de email de confirmação de pedido usando o padrão Template Method
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void emailDeConfirmacaoDePedido(Pedido obj) {
		
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
		
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
}
