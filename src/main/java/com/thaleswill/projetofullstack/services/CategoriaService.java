package com.thaleswill.projetofullstack.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.thaleswill.projetofullstack.domain.Categoria;
import com.thaleswill.projetofullstack.dto.CategoriaDTO;
import com.thaleswill.projetofullstack.repositories.CategoriaRepository;
import com.thaleswill.projetofullstack.services.exceptions.DataIntegrityException;
import com.thaleswill.projetofullstack.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Essa categoria não está vazia! Operação não realizada!");
		}
	}
	
	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	//Paginação da lista de categorias
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//método auxiliar para converter objetos do tipo CategoriaDTO
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	// método auxiliar para atualizar os dados de 'newObj' contendo os dados buscados no BD
		private void updateData(Categoria newObj, Categoria obj) {
			newObj.setNome(obj.getNome());
		}
}
