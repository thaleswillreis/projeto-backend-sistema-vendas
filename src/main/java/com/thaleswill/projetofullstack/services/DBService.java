package com.thaleswill.projetofullstack.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
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
	
	@Value("${default.recipient}")
	private String emailDestino;

	public void instantiateTestDatabase() throws ParseException {

		// dados mock categoria e produto
		Categoria catg1 = new Categoria(null, "Informática");
		Categoria catg2 = new Categoria(null, "Escritório");
		Categoria catg3 = new Categoria(null, "Games");
		Categoria catg4 = new Categoria(null, "Eletrônicos");
		Categoria catg5 = new Categoria(null, "Ferramentas");
		Categoria catg6 = new Categoria(null, "Perfumaria");
		Categoria catg7 = new Categoria(null, "Eletrodomésticos");
		Categoria catg8 = new Categoria(null, "Petshop");

		Produto prod1 = new Produto(null, "Notebook Lenovo", 3000.00);
		Produto prod2 = new Produto(null, "Battlefield One", 60.00);
		Produto prod3 = new Produto(null, "Impressora", 800.00);
		Produto prod4 = new Produto(null, "Apple IPad Pro", 2800.00);
		Produto prod5 = new Produto(null, "Mouse Gamer", 120.00);
		Produto prod6 = new Produto(null, "Micro retífica Dremel", 450.00);
		Produto prod7 = new Produto(null, "Ferrari Black 125ml", 150.00);
		Produto prod8 = new Produto(null, "Fogão 4 bocas", 800.00);
		Produto prod9 = new Produto(null, "Coleira Antiparasitária", 80.67);
		Produto prod10 = new Produto(null, "Papel Report Mult A4", 26.90);
		Produto prod11 = new Produto(null, "gift card xbox game pass 12 meses", 229.00);
		Produto prod12 = new Produto(null, "Microondas Grill LG 30L", 669.00);
		Produto prod13 = new Produto(null, "Paco Rabanne Invictus Platinum 100ml", 533.90);
		Produto prod14 = new Produto(null, "Headset ASTRO Gaming A40 TR + MixAmp Pro TR Gen 4", 1580.00);
		Produto prod15 = new Produto(null, "Ração Golden Seleção Natural Cães Adultos 3kg", 59.90);

		catg1.getProdutos().addAll(Arrays.asList(prod1, prod3, prod4, prod5, prod14));
		catg2.getProdutos().addAll(Arrays.asList(prod3, prod10));
		catg3.getProdutos().addAll(Arrays.asList(prod2, prod5, prod11, prod14));
		catg4.getProdutos().addAll(Arrays.asList(prod4, prod14));
		catg5.getProdutos().addAll(Arrays.asList(prod6));
		catg6.getProdutos().addAll(Arrays.asList(prod7, prod13));
		catg7.getProdutos().addAll(Arrays.asList(prod8, prod12));
		catg8.getProdutos().addAll(Arrays.asList(prod9, prod15));
		prod1.getCategorias().addAll(Arrays.asList(catg1));
		prod2.getCategorias().addAll(Arrays.asList(catg3));
		prod3.getCategorias().addAll(Arrays.asList(catg1, catg2));
		prod4.getCategorias().addAll(Arrays.asList(catg1, catg4));
		prod5.getCategorias().addAll(Arrays.asList(catg1, catg3));
		prod6.getCategorias().addAll(Arrays.asList(catg5));
		prod7.getCategorias().addAll(Arrays.asList(catg6));
		prod8.getCategorias().addAll(Arrays.asList(catg7));
		prod9.getCategorias().addAll(Arrays.asList(catg8));
		prod10.getCategorias().addAll(Arrays.asList(catg2));
		prod11.getCategorias().addAll(Arrays.asList(catg3));
		prod12.getCategorias().addAll(Arrays.asList(catg7));
		prod13.getCategorias().addAll(Arrays.asList(catg6));
		prod14.getCategorias().addAll(Arrays.asList(catg1, catg3, catg4));
		prod15.getCategorias().addAll(Arrays.asList(catg8));

		categoriaRepository.saveAll(Arrays.asList(catg1, catg2, catg3, catg4, catg5, catg6, catg7, catg8));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9, prod10,
				prod11, prod12, prod13, prod14, prod15));

		// dados mock cliente e endereco
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

		Cliente cliente1 = new Cliente(null, "Maninho", emailDestino, "87061325372", TipoCliente.PESSOAFISICA, passwordEncoder.encode("123456"));
		cliente1.getTelefones().addAll(Arrays.asList("99991001122", "99984001122"));

		Endereco end1 = new Endereco(null, "Rua A", "100", "Apto 102", "Vila do rato", "65900000", cliente1, mun1);
		Endereco end2 = new Endereco(null, "Rua B", "200", "Apto 204", "Centro", "65900000", cliente1, mun2);

		cliente1.getEnderecos().addAll(Arrays.asList(end1, end2));

		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));

		// dados mock pedido e pagamento
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("11/10/2022 11:30"), cliente1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("12/10/2022 21:04"), cliente1, end2);

		cliente1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("12/10/2022 21:09"),
				null);

		ped2.setPagamento(pagto2);

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		// dados mock items de pedido
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
