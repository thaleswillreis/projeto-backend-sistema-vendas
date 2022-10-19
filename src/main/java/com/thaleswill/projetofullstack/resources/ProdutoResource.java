package com.thaleswill.projetofullstack.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thaleswill.projetofullstack.domain.Produto;
import com.thaleswill.projetofullstack.dto.ProdutoDTO;
import com.thaleswill.projetofullstack.resources.utils.URL;
import com.thaleswill.projetofullstack.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	//Busca paginada por letras, nomes e categorias
		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity<Page<ProdutoDTO>> findPage(
				@RequestParam(value = "nome", defaultValue = "") String nome, 
				@RequestParam(value = "categorias", defaultValue = "") String categorias, 
				@RequestParam(value = "page", defaultValue = "0") Integer page, 
				@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
				@RequestParam(value = "direction", defaultValue = "ASC") String direction, 
				@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
			String nomeDecoded = URL.decodeParam(nome);
			List<Integer> ids = URL.decodeIntList(categorias);
			Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, direction, orderBy);
			Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
			return ResponseEntity.ok().body(listDto);
		}
	}
