package com.udemy.spring;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.transaction.Transactional;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
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

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setLazyInitialization(true);
        app.run(args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama, Mesa e Banho");
        Categoria cat4 = new Categoria(null, "Eletrônicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Decoração");
        Categoria cat7 = new Categoria(null, "Perfumaria");

        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);

        cat1.produtos.addAll(Arrays.asList(p1, p2, p3));
        cat2.produtos.addAll(Arrays.asList(p2));

        p1.categorias.addAll(Arrays.asList(cat1));
        p2.categorias.addAll(Arrays.asList(cat1, cat2));
        p3.categorias.addAll(Arrays.asList(cat1));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
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

        Pagamento pagto2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, null,
                sdf.parse("20/10/2017 00:00"));
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