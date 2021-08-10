package com.udemy.spring.services;

import java.util.List;
import java.util.Optional;

import com.udemy.spring.domain.Pedido;
import com.udemy.spring.exceptions.ObjectNotFoundException;
import com.udemy.spring.repositories.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    public List<Pedido> Listar() {
        List<Pedido> pedidos = repository.findAll();
        return pedidos;
    }

    public Pedido Buscar(Integer id) {
        Optional<Pedido> pedido = repository.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Pedido.class.getName())));
    }
}