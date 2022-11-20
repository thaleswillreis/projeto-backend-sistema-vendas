package com.thaleswill.projetofullstack.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	//decodifica String com caracteres especiais para o padrão correto
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	//Converte uma String de números separados por vírgula em lista de Inteiros.
	public static List<Integer> decodeIntList(String s) {
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
	}
}