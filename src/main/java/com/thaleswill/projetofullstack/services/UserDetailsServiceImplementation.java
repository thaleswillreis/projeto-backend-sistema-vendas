package com.thaleswill.projetofullstack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thaleswill.projetofullstack.domain.Cliente;
import com.thaleswill.projetofullstack.repositories.ClienteRepository;
import com.thaleswill.projetofullstack.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImplementation implements  UserDetailsService {
	
	@Autowired
	private ClienteRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente =repo.findByEmail(email);
		if (cliente == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSpringSecurity(
				cliente.getId(), 
				cliente.getEmail(), 
				cliente.getSenha(), 
				cliente.getPerfis()
				);
	}
}
