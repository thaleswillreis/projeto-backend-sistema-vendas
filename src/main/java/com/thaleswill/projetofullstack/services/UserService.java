package com.thaleswill.projetofullstack.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.thaleswill.projetofullstack.security.UserSpringSecurity;

public class UserService {

	public static UserSpringSecurity authenticated() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().
					getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
