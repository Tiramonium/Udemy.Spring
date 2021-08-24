package com.udemy.spring.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;

import com.udemy.spring.domain.Cliente;
import com.udemy.spring.domain.Endereco;
import com.udemy.spring.domain.Telefone;
import com.udemy.spring.exceptions.DataIntegrityException;
import com.udemy.spring.exceptions.ObjectNotFoundException;
import com.udemy.spring.helpers.PageRequestHelper;
import com.udemy.spring.repositories.CidadeRepository;
import com.udemy.spring.repositories.ClienteRepository;
import com.udemy.spring.repositories.EnderecoRepository;
import com.udemy.spring.repositories.EstadoRepository;
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
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

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

    public Page<Cliente> Paginar(Integer pagina, Integer linhasPorPagina, String colunaOrdenacao, String tipoOrdenacao) {
        PageRequest pageRequest = new PageRequestHelper(pagina, linhasPorPagina, colunaOrdenacao, tipoOrdenacao).Paginate();
        Page<Cliente> clientes = repository.findAll(pageRequest).map(cliente -> cliente.LazyLoad());
        return clientes;
    }

    public Cliente Buscar(Integer id) {
        logger.info(String.format("Buscar Id: %d", id));
        Optional<Cliente> clienteResultado = repository.findById(id);

        clienteResultado
            .orElseThrow(() -> new ObjectNotFoundException(String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Cliente.class.getName())));

        clienteResultado.map(cliente -> cliente.enderecos = enderecoRepository.findAllByCliente(id));
        clienteResultado.map(cliente -> cliente.enderecos.stream().map(endereco -> {
            endereco.cidade.estado.LazyLoad();
            endereco.cliente = null;
            return endereco;
        }).collect(Collectors.toList()));

        clienteResultado.map(cliente -> cliente.pedidos = pedidoRepository.findAllByCliente(id));
        clienteResultado.map(cliente -> cliente.pedidos.stream().map(pedido -> pedido.LazyLoad()).collect(Collectors.toList()));

        clienteResultado.map(cliente -> cliente.telefones = telefoneRepository.findAllByCliente(id));
        clienteResultado.map(cliente -> cliente.telefones.stream().map(telefone -> telefone.LazyLoad()).collect(Collectors.toList()));

        return clienteResultado.get();
    }

    @Transactional
    public Cliente Cadastrar(Cliente cliente) {
        cliente.id = null;

        if (repository.findByCpfCnpj(cliente.cpfOuCnpj).isPresent())
        {
            throw new EntityExistsException(String.format("O Cliente de CPF ou CNPJ %s já está cadastrado no sistema", cliente.cpfOuCnpj));
        }

        cliente = repository.save(cliente);

        for (Endereco endereco : cliente.enderecos)
        {
            endereco.cidade.estado = estadoRepository.findByNome(endereco.cidade.estado.nome).orElse(estadoRepository.save(endereco.cidade.estado));
            endereco.cidade = cidadeRepository.findByNome(endereco.cidade.nome).orElse(cidadeRepository.save(endereco.cidade));
            endereco.cliente = cliente;
            endereco = enderecoRepository.save(endereco);
        }

        for (Telefone telefone : cliente.telefones)
        {
            telefone.cliente = cliente;
            telefone = telefoneRepository.findById(telefone.telefone).orElse(telefoneRepository.save(telefone));
        }

        return cliente;
    }

    @Transactional
    public Cliente Atualizar(Integer id, Cliente novoCliente) {
        novoCliente.id = id;
        Cliente cliente = Buscar(id).Atualizar(novoCliente);

        for (Endereco endereco : cliente.enderecos)
        {
            endereco.cidade.estado = endereco.cidade.estado.id != null
                ? estadoRepository.findById(endereco.cidade.estado.id).orElse(estadoRepository.save(endereco.cidade.estado))
                : estadoRepository.findByNome(endereco.cidade.estado.nome).orElse(estadoRepository.save(endereco.cidade.estado));
            endereco.cidade =
                endereco.cidade.id != null ? cidadeRepository.findById(endereco.cidade.id).orElse(cidadeRepository.save(endereco.cidade))
                    : cidadeRepository.findByNome(endereco.cidade.nome).orElse(cidadeRepository.save(endereco.cidade));
            endereco.cliente = cliente;
            endereco = enderecoRepository.save(endereco);
        }

        for (Telefone telefone : cliente.telefones)
        {
            telefone.cliente = cliente;
            telefone = telefoneRepository.findById(telefone.telefone).orElse(telefoneRepository.save(telefone));
        }

        cliente = repository.save(cliente);
        return cliente;
    }

    public void Apagar(Integer id) {
        Buscar(id);

        try
        {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException ex)
        {
            throw new DataIntegrityException(
                String.format("Não é possível excluir porque há entidades relacionadas! Id: %d, Tipo: %s", id, Cliente.class.getName()));
        }
    }
}