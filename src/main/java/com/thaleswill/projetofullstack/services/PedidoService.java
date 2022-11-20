package com.thaleswill.projetofullstack.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thaleswill.projetofullstack.domain.Cliente;
import com.thaleswill.projetofullstack.domain.ItemPedido;
import com.thaleswill.projetofullstack.domain.PagamentoComBoleto;
import com.thaleswill.projetofullstack.domain.Pedido;
import com.thaleswill.projetofullstack.domain.enums.EstadoPagamento;
import com.thaleswill.projetofullstack.repositories.ItemPedidoRepository;
import com.thaleswill.projetofullstack.repositories.PagamentoRepository;
import com.thaleswill.projetofullstack.repositories.PedidoRepository;
import com.thaleswill.projetofullstack.security.UserSpringSecurity;
import com.thaleswill.projetofullstack.services.exceptions.AuthorizationException;
import com.thaleswill.projetofullstack.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstadoPag(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItems()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItems());
		emailService.emailDeConfirmacaoDePedidoHtml(obj);
		return obj;
	}
	
	//verifica qual o cliente está autenticado e retorna somente os pedidos dele
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		UserSpringSecurity user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
}