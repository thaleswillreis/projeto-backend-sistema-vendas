package com.thaleswill.projetofullstack.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thaleswill.projetofullstack.domain.Cidade;
import com.thaleswill.projetofullstack.domain.Cliente;
import com.thaleswill.projetofullstack.domain.Endereco;
import com.thaleswill.projetofullstack.domain.enums.TipoCliente;
import com.thaleswill.projetofullstack.dto.ClienteDTO;
import com.thaleswill.projetofullstack.dto.ClienteNovoDTO;
import com.thaleswill.projetofullstack.repositories.ClienteRepository;
import com.thaleswill.projetofullstack.repositories.EnderecoRepository;
import com.thaleswill.projetofullstack.services.exceptions.DataIntegrityException;
import com.thaleswill.projetofullstack.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Essa Cliente não está vazia! Operação não realizada!");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	// Paginação da lista de Clientes
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	// método auxiliar para converter objetos do tipo ClienteDTO
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}

	// sobrecarga do método auxiliar para converter objetos do tipo ClienteNovoDTO
	public Cliente fromDTO(ClienteNovoDTO objDto) {		
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), 
				objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), passwordEncoder.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), 
				objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	// método auxiliar para atualizar os dados de 'newObj' contendo os dados
	// buscados no BD
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}