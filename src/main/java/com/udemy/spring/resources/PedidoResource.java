package com.udemy.spring.resources;

import java.util.List;

import com.udemy.spring.domain.Pedido;
import com.udemy.spring.services.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
    @Autowired
    private PedidoService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Pedido> Listar() {
        List<Pedido> pedidos = service.Listar();
        return pedidos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> Buscar(@PathVariable Integer id) {
        Pedido pedido = service.Buscar(id);
        return ResponseEntity.ok().body(pedido);
    }
}