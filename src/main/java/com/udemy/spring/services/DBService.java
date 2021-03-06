package com.udemy.spring.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;

import com.udemy.spring.domain.Categoria;
import com.udemy.spring.domain.Cidade;
import com.udemy.spring.domain.Cliente;
import com.udemy.spring.domain.Endereco;
import com.udemy.spring.domain.Estado;
import com.udemy.spring.domain.ItemPedido;
import com.udemy.spring.domain.Pagamento;
import com.udemy.spring.domain.PagamentoBoleto;
import com.udemy.spring.domain.PagamentoCartao;
import com.udemy.spring.domain.Pedido;
import com.udemy.spring.domain.Produto;
import com.udemy.spring.domain.Telefone;
import com.udemy.spring.enums.EstadoPagamento;
import com.udemy.spring.enums.TipoCliente;
import com.udemy.spring.repositories.CategoriaRepository;
import com.udemy.spring.repositories.CidadeRepository;
import com.udemy.spring.repositories.ClienteRepository;
import com.udemy.spring.repositories.EnderecoRepository;
import com.udemy.spring.repositories.EstadoRepository;
import com.udemy.spring.repositories.ItemPedidoRepository;
import com.udemy.spring.repositories.PagamentoRepository;
import com.udemy.spring.repositories.PedidoRepository;
import com.udemy.spring.repositories.ProdutoRepository;
import com.udemy.spring.repositories.TelefoneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DBService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Transactional
    public void IniciarBanco() throws ParseException {
        Categoria cat1 = new Categoria(null, "Inform??tica");
        Categoria cat2 = new Categoria(null, "Escrit??rio");
        Categoria cat3 = new Categoria(null, "Cama, Mesa e Banho");
        Categoria cat4 = new Categoria(null, "Eletr??nicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Decora????o");
        Categoria cat7 = new Categoria(null, "Perfumaria");

        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);
        Produto p4 = new Produto(null, "Mesa de escrit??rio", 300.00);
        Produto p5 = new Produto(null, "Toalha", 50.00);
        Produto p6 = new Produto(null, "Colcha", 200.00);
        Produto p7 = new Produto(null, "TV true color", 1200.00);
        Produto p8 = new Produto(null, "Ro??adeira", 800.00);
        Produto p9 = new Produto(null, "Abajour", 100.00);
        Produto p10 = new Produto(null, "Pendente", 180.00);
        Produto p11 = new Produto(null, "Shampoo", 90.00);

        cat1.produtos.addAll(Arrays.asList(p1, p2, p3));
        cat2.produtos.addAll(Arrays.asList(p2, p4));
        cat3.produtos.addAll(Arrays.asList(p5, p6));
        cat4.produtos.addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.produtos.addAll(Arrays.asList(p8));
        cat6.produtos.addAll(Arrays.asList(p9, p10));
        cat7.produtos.addAll(Arrays.asList(p11));

        p1.categorias.addAll(Arrays.asList(cat1, cat4));
        p2.categorias.addAll(Arrays.asList(cat1, cat2, cat4));
        p3.categorias.addAll(Arrays.asList(cat1, cat4));
        p4.categorias.addAll(Arrays.asList(cat2));
        p5.categorias.addAll(Arrays.asList(cat3));
        p6.categorias.addAll(Arrays.asList(cat3));
        p7.categorias.addAll(Arrays.asList(cat4));
        p8.categorias.addAll(Arrays.asList(cat5));
        p9.categorias.addAll(Arrays.asList(cat6));
        p10.categorias.addAll(Arrays.asList(cat6));
        p11.categorias.addAll(Arrays.asList(cat7));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "S??o Paulo");

        Cidade c1 = new Cidade(null, "Uberl??ndia", est1);
        Cidade c2 = new Cidade(null, "S??o Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.cidades.addAll(Arrays.asList(c1));
        est2.cidades.addAll(Arrays.asList(c2, c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "10090990005", TipoCliente.PESSOAFISICA);

        Telefone tel1 = new Telefone("27363323", cli1);
        Telefone tel2 = new Telefone("93838393", cli1);

        cli1.telefones.addAll(Arrays.asList(tel1, tel2));

        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", c1, cli1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", c2, cli1);

        cli1.enderecos.addAll(Arrays.asList(e1, e2));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));
        telefoneRepository.saveAll(Arrays.asList(tel1, tel2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

        Pagamento pagto1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, ped1, sdf.parse("30/09/2017 10:33"), 6);
        ped1.pagamentos.add(pagto1);

        Pagamento pagto2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, null, sdf.parse("20/10/2017 00:00"));
        ped2.pagamentos.add(pagto2);

        cli1.pedidos.addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
        clienteRepository.save(cli1);

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

        ped1.itens.addAll(Arrays.asList(ip1, ip2));
        ped2.itens.add(ip3);

        p1.itens.add(ip1);
        p2.itens.add(ip3);
        p3.itens.add(ip2);

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
    }
}