package com.udemy.spring.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.udemy.spring.domain.Cliente;
import com.udemy.spring.exceptions.DataIntegrityException;
import com.udemy.spring.exceptions.ObjectNotFoundException;
import com.udemy.spring.helpers.PaginaHelper;
import com.udemy.spring.repositories.ClienteRepository;
import com.udemy.spring.repositories.EnderecoRepository;
import com.udemy.spring.repositories.PedidoRepository;
import com.udemy.spring.repositories.TelefoneRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    private Logger logger = LogManager.getLogger(ClienteService.class);

    public List<Cliente> Listar() {
        List<Cliente> clientes = repository.findAll();
        clientes = clientes.stream().map(cliente -> cliente.LazyLoad()).collect(Collectors.toList());
        return clientes;
    }

    public Page<Cliente> Paginar(Integer pagina, Integer linhasPorPagina, String colunaOrdenacao,
            String tipoOrdenacao) {
        PageRequest pageRequest = new PaginaHelper(pagina, linhasPorPagina, colunaOrdenacao, tipoOrdenacao).Paginar();
        Page<Cliente> clientes = repository.findAll(pageRequest).map(cliente -> cliente.LazyLoad());
        return clientes;
    }

    public Cliente Buscar(Integer id) {
        logger.info(String.format("Buscar Id: %d", id));
        Optional<Cliente> clienteResultado = repository.findById(id);

        clienteResultado.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Cliente.class.getName())));

        clienteResultado.map(cliente -> cliente.enderecos = enderecoRepository.findAllByCliente(id));
        clienteResultado.map(cliente -> cliente.enderecos.stream().map(endereco -> {
            endereco.cidade.estado.LazyLoad();
            endereco.cliente = null;
            return endereco;
        }).collect(Collectors.toList()));

        clienteResultado.map(cliente -> cliente.pedidos = pedidoRepository.findAllByCliente(id));
        clienteResultado
                .map(cliente -> cliente.pedidos.stream().map(pedido -> pedido.LazyLoad()).collect(Collectors.toList()));

        clienteResultado.map(cliente -> cliente.telefones = telefoneRepository.findAllByCliente(id));
        clienteResultado.map(cliente -> cliente.telefones.stream().map(telefone -> telefone.LazyLoad())
                .collect(Collectors.toList()));

        return clienteResultado.get();
    }

    @Transactional
    public Cliente Cadastrar(Cliente cliente) {
        cliente.id = null;
        return repository.save(cliente);
    }

    @Transactional
    public Cliente Atualizar(Cliente novoCliente) {
        Cliente cliente = Buscar(novoCliente.id);
        cliente.Atualizar(novoCliente);
        return repository.save(cliente);
    }

    public void Apagar(Integer id) {
        Buscar(id);

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException(
                    String.format("Não é possível excluir porque há entidades relacionadas! Id: %d, Tipo: %s", id,
                            Cliente.class.getName()));
        }
    }
}