package com.udemy.spring.resources;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import com.udemy.spring.domain.Pedido;
import com.udemy.spring.services.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> Cadastrar(@Valid @RequestBody Pedido pedido) {
        pedido = service.Cadastrar(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.id).toUri();
        return ResponseEntity.created(uri).body(pedido);
    }
}