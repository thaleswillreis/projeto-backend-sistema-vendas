package com.thaleswill.projetofullstack;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thaleswill.projetofullstack.domain.Categoria;
import com.thaleswill.projetofullstack.domain.Cidade;
import com.thaleswill.projetofullstack.domain.Cliente;
import com.thaleswill.projetofullstack.domain.Endereco;
import com.thaleswill.projetofullstack.domain.Estado;
import com.thaleswill.projetofullstack.domain.ItemPedido;
import com.thaleswill.projetofullstack.domain.Pagamento;
import com.thaleswill.projetofullstack.domain.PagamentoComBoleto;
import com.thaleswill.projetofullstack.domain.PagamentoComCartao;
import com.thaleswill.projetofullstack.domain.Pedido;
import com.thaleswill.projetofullstack.domain.Produto;
import com.thaleswill.projetofullstack.domain.enums.EstadoPagamento;
import com.thaleswill.projetofullstack.domain.enums.TipoCliente;
import com.thaleswill.projetofullstack.repositories.CategoriaRepository;
import com.thaleswill.projetofullstack.repositories.CidadeRepository;
import com.thaleswill.projetofullstack.repositories.ClienteRepository;
import com.thaleswill.projetofullstack.repositories.EnderecoRepository;
import com.thaleswill.projetofullstack.repositories.EstadoRepository;
import com.thaleswill.projetofullstack.repositories.ItemPedidoRepository;
import com.thaleswill.projetofullstack.repositories.PagamentoRepository;
import com.thaleswill.projetofullstack.repositories.PedidoRepository;
import com.thaleswill.projetofullstack.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjetofullstackApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetofullstackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//dados mock categoria e produto
		Categoria cat1 =new Categoria(null, "Informática");
		Categoria cat2 =new Categoria(null, "Escritório");
		Categoria cat3 =new Categoria(null, "Games");
		Categoria cat4 =new Categoria(null, "Eletrônicos");
		
		Produto prod1 =new Produto(null, "Notebook Lenovo", 3000.00);
		Produto prod2 =new Produto(null, "Battlefield One", 60.00);
		Produto prod3 =new Produto(null, "Impressora", 800.00);
		Produto prod4 =new Produto(null, "Apple IPad Pro", 2800.00);
		Produto prod5 =new Produto(null, "Mouse Gamer", 120.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod3, prod4, prod5));
		cat2.getProdutos().addAll(Arrays.asList(prod3));
		cat3.getProdutos().addAll(Arrays.asList(prod2, prod5));
		cat4.getProdutos().addAll(Arrays.asList(prod4));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat3));
		prod3.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod4.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prod5.getCategorias().addAll(Arrays.asList(cat1, cat3));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3, prod4, prod5));

		//dados mock cliente e endereco
		Estado uf1 = new Estado(null, "Maranhão");
		Estado uf2 = new Estado(null, "São Paulo");
		Estado uf3 = new Estado(null, "Paraná");
		
		Cidade mun1 = new Cidade(null, "Imperatriz", uf1);
		Cidade mun2 = new Cidade(null, "Indaiatuba", uf2);
		Cidade mun3 = new Cidade(null, "Maringá", uf3);
		Cidade mun4 = new Cidade(null, "Jundiaí", uf2);
		
		uf1.getCidades().addAll(Arrays.asList(mun1));
		uf2.getCidades().addAll(Arrays.asList(mun2, mun4));
		uf3.getCidades().addAll(Arrays.asList(mun3));
		
		estadoRepository.saveAll(Arrays.asList(uf1, uf2, uf3));
		cidadeRepository.saveAll(Arrays.asList(mun1, mun2, mun3, mun4));
		
		Cliente cliente1 = new Cliente(null, "Joaozinho", "joao@gmail.com", "22233344455", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("99991001122", "99984001122"));
		
		Endereco end1 = new Endereco(null, "Rua A", "100", "Apto 102", "Vila do rato", "65900000", cliente1, mun1);
		Endereco end2 = new Endereco(null, "Rua B", "200", "Apto 204", "Centro", "65900000", cliente1, mun2);

		cliente1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		
		//dados mock pedido e pagamento
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("11/10/2022 11:30"), cliente1, end1); 
		Pedido ped2 = new Pedido(null, sdf.parse("12/10/2022 21:04"), cliente1, end2); 
		 
		cliente1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6); 
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("12/10/2022 21:09"), null); 
				ped2.setPagamento(pagto2);
				
				pedidoRepository.saveAll(Arrays.asList(ped1, ped2)); 
				pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
				
		//dados mock items de pedido
				
		ItemPedido itemped1 = new ItemPedido(ped1, prod1, 0.00, 1, 3000.00);
		ItemPedido itemped2 = new ItemPedido(ped1, prod2, 0.00, 2, 60.00);
		ItemPedido itemped3 = new ItemPedido(ped2, prod3, 40.00, 1, 800.00);
		
		ped1.getItems().addAll(Arrays.asList(itemped1, itemped2));
		ped2.getItems().addAll(Arrays.asList(itemped3));
		
		prod1.getItems().addAll(Arrays.asList(itemped1));
		prod2.getItems().addAll(Arrays.asList(itemped2));
		prod3.getItems().addAll(Arrays.asList(itemped3));
		
		itemPedidoRepository.saveAll(Arrays.asList(itemped1, itemped2, itemped3));
	}
}
