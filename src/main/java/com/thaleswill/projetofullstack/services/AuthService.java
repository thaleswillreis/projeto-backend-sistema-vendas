package com.thaleswill.projetofullstack.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.thaleswill.projetofullstack.domain.Cliente;
import com.thaleswill.projetofullstack.repositories.ClienteRepository;
import com.thaleswill.projetofullstack.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;
	
	private Random randon = new Random();

	public void sendNewPassword(String email) {

		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}

		String newPass = newPassword();
		cliente.setSenha(passwordEncoder.encode(newPass));

		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	//gera cadeias de Strings aleatórias de tamanho 10, com caracteres Unicode
	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}
	
	//gera caracteres de acordo com a tabela Unicode
	private char randomChar() {
		int opt = randon.nextInt(3);
		if (opt == 0) { // gera um digito (0-9) a partir do codigo 48 unicode (numeros)
			return (char) (randon.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula(A-Z) a partir do codigo 65 unicode (Letras Maiúsculas)
			return (char) (randon.nextInt(26) + 65);
		}
		else { // gera letra minuscula(a-z) a partir do codigo 97 unicode (Letras Minúsculas)
			return (char) (randon.nextInt(26) + 97);
		}
	}

}
