package com.thaleswill.projetofullstack.services;

import org.springframework.mail.SimpleMailMessage;

import com.thaleswill.projetofullstack.domain.Pedido;

public interface EmailService {
	
	void emailDeConfirmacaoDePedido(Pedido obj);
	void sendEmail(SimpleMailMessage msg);

}
