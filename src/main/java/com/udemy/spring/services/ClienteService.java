package com.udemy.spring.services;

import java.util.List;
import java.util.Optional;

import com.udemy.spring.domain.Cliente;
import com.udemy.spring.exceptions.ObjectNotFoundException;
import com.udemy.spring.repositories.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public List<Cliente> Listar() {
        List<Cliente> clientes = repository.findAll();
        return clientes;
    }

    public Cliente Buscar(Integer id) {
        Optional<Cliente> cliente = repository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Cliente.class.getName())));
    }
}