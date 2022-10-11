package com.thaleswill.projetofullstack;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thaleswill.projetofullstack.domain.Categoria;
import com.thaleswill.projetofullstack.domain.Produto;
import com.thaleswill.projetofullstack.repositories.CategoriaRepository;
import com.thaleswill.projetofullstack.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjetofullstackApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetofullstackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 =new Categoria(null, "Informática");
		Categoria cat2 =new Categoria(null, "Escritório");
		Categoria cat3 =new Categoria(null, "Games");
		Categoria cat4 =new Categoria(null, "Eletrônicos");
		
		Produto p1 =new Produto(null, "Notebook Lenovo", 3000.00);
		Produto p2 =new Produto(null, "Battlefield One", 60.00);
		Produto p3 =new Produto(null, "Impressora", 800.00);
		Produto p4 =new Produto(null, "Apple IPad Pro", 2800.00);
		Produto p5 =new Produto(null, "Mouse Gamer", 120.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p3, p4, p5));
		cat2.getProdutos().addAll(Arrays.asList(p3));
		cat3.getProdutos().addAll(Arrays.asList(p2, p5));
		cat4.getProdutos().addAll(Arrays.asList(p4));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat3));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p4.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p5.getCategorias().addAll(Arrays.asList(cat1, cat3));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
	}
}
