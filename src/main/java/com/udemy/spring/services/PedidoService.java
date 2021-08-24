package com.udemy.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.udemy.spring.domain.Cliente;
import com.udemy.spring.domain.Pedido;
import com.udemy.spring.exceptions.ObjectNotFoundException;
import com.udemy.spring.repositories.EnderecoRepository;
import com.udemy.spring.repositories.ItemPedidoRepository;
import com.udemy.spring.repositories.PagamentoRepository;
import com.udemy.spring.repositories.PedidoRepository;
import com.udemy.spring.repositories.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    public List<Pedido> Listar() {
        List<Pedido> pedidos = repository.findAll();
        pedidos = pedidos.stream().map(pedido -> pedido.LazyLoad()).collect(Collectors.toList());
        return pedidos;
    }

    public Pedido Buscar(Integer id) {
        Optional<Pedido> pedidoResultado = repository.findEagerById(id);

        pedidoResultado
            .orElseThrow(() -> new ObjectNotFoundException(String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Pedido.class.getName())));

        if (!pedidoResultado.map(pedido -> pedido.cliente).isPresent())
        {
            throw new ObjectNotFoundException(String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Cliente.class.getName()));
        }

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
            if (pedido.enderecoEntrega == null)
            {
                return pedido;
            }

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
}