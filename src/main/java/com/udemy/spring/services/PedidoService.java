package com.udemy.spring.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.udemy.spring.domain.ItemPedido;
import com.udemy.spring.domain.Pagamento;
import com.udemy.spring.domain.PagamentoBoleto;
import com.udemy.spring.domain.PagamentoCartao;
import com.udemy.spring.domain.Pedido;
import com.udemy.spring.enums.EstadoPagamento;
import com.udemy.spring.exceptions.ObjectNotFoundException;
import com.udemy.spring.repositories.ClienteRepository;
import com.udemy.spring.repositories.EnderecoRepository;
import com.udemy.spring.repositories.ItemPedidoRepository;
import com.udemy.spring.repositories.PagamentoRepository;
import com.udemy.spring.repositories.PedidoRepository;
import com.udemy.spring.repositories.ProdutoRepository;
import com.udemy.spring.repositories.TelefoneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    public List<Pedido> Listar() {
        List<Pedido> pedidos = repository.findAll();
        pedidos = pedidos.stream().map(pedido -> pedido.LazyLoad()).collect(Collectors.toList());
        return pedidos;
    }

    public Pedido Buscar(Integer id) {
        Optional<Pedido> pedidoResultado = repository.findById(id);

        pedidoResultado
            .orElseThrow(() -> new ObjectNotFoundException(String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Pedido.class.getName())));

        pedidoResultado.map(pedido -> pedido.cliente.pedidos = new ArrayList<Pedido>());

        pedidoResultado.map(pedido -> pedido.cliente.enderecos = enderecoRepository.findAllByCliente(pedido.cliente.id));
        pedidoResultado.map(pedido -> pedido.cliente.enderecos.stream().map(endereco -> {
            endereco.cidade.estado.LazyLoad();
            endereco.cliente = null;
            return endereco;
        }).collect(Collectors.toList()));

        pedidoResultado.map(pedido -> pedido.cliente.telefones = telefoneRepository.findAllByCliente(pedido.cliente.id));
        pedidoResultado.map(pedido -> pedido.cliente.telefones.stream().map(telefone -> telefone.LazyLoad()).collect(Collectors.toList()));

        pedidoResultado.map(pedido -> {
            pedido.enderecoEntrega.cidade.estado.LazyLoad();
            pedido.enderecoEntrega.cliente = null;
            return pedido;
        });

        pedidoResultado.map(pedido -> pedido.itens = itemPedidoRepository.findAllByPedido(pedido.id));
        pedidoResultado.map(pedido -> pedido.itens.stream().map(item -> {
            item.produto.LazyLoad();
            item.pedido = null;
            return item;
        }).collect(Collectors.toList()));

        pedidoResultado.map(pedido -> pedido.pagamentos = pagamentoRepository.findAllByPedido(pedido.id));
        pedidoResultado.map(pedido -> pedido.pagamentos.stream().map(pagamento -> pagamento.LazyLoad()).collect(Collectors.toList()));

        return pedidoResultado.get();
    }

    @Transactional
    public Pedido Cadastrar(Pedido pedido) {
        pedido.id = null;
        pedido.dataCadastro = new Date();

        Integer clienteId = pedido.cliente.id;
        Integer enderecoId = pedido.enderecoEntrega.id;

        clienteRepository.findById(clienteId)
            .orElseThrow(() -> new ObjectNotFoundException(String.format("O Cliente %d não foi encontrado", clienteId)));
        enderecoRepository.findById(enderecoId)
            .orElseThrow(() -> new ObjectNotFoundException(String.format("O Endereço %d não foi encontrado", enderecoId)));

        Pagamento pagamento = pedido.pagamentos.get(0);
        pagamento.setEstado(EstadoPagamento.PENDENTE);
        pagamento.pedido = pedido;

        if (pagamento instanceof PagamentoBoleto)
        {
            PagamentoBoleto pagamentoBoleto = (PagamentoBoleto) pagamento;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(pedido.dataCadastro);
            calendar.add(Calendar.DAY_OF_MONTH, 7);

            pagamentoBoleto.dataVencimento = calendar.getTime();
            pagamentoBoleto = pagamentoRepository.save(pagamentoBoleto);
            pedido.pagamentos.set(0, pagamentoBoleto);
        }
        else if (pagamento instanceof PagamentoCartao)
        {
            PagamentoCartao pagamentoCartao = (PagamentoCartao) pagamento;
            pagamentoCartao = pagamentoRepository.save(pagamentoCartao);
            pedido.pagamentos.set(0, pagamentoCartao);
        }

        pedido = repository.save(pedido);

        for (ItemPedido ip : pedido.itens)
        {
            ip.desconto = 0d;
            ip.preco = produtoRepository.findById(ip.produto.id).get().preco;
            ip.pedido = pedido;
        }

        itemPedidoRepository.saveAll(pedido.itens);

        pedido.cliente = clienteRepository.findById(pedido.cliente.id).get();
        pedido.cliente.enderecos.stream().map((endereco) -> endereco.cidade.estado.LazyLoad()).collect(Collectors.toList());
        pedido.cliente.telefones.stream().map((telefone) -> telefone.LazyLoad()).collect(Collectors.toList());
        pedido.cliente.pedidos = new ArrayList<Pedido>();

        pedido.enderecoEntrega = enderecoRepository.findById(pedido.enderecoEntrega.id).get();
        pedido.enderecoEntrega.cidade.estado.LazyLoad();

        pedido.itens.stream().map((item) -> item.produto = produtoRepository.findById(item.produto.id).get().LazyLoad()).collect(Collectors.toList());
        emailService.sendOrderConfirmationEmailHtml(pedido);
        return pedido;
    }
}