package com.udemy.spring.resources;

import java.util.List;

import com.udemy.spring.domain.Cliente;
import com.udemy.spring.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
    @Autowired
    private ClienteService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Cliente> Listar() {
        List<Cliente> clientes = service.Listar();
        return clientes;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> Buscar(@PathVariable Integer id) {
        Cliente cliente = service.Buscar(id);
        return ResponseEntity.ok().body(cliente);
    }
}