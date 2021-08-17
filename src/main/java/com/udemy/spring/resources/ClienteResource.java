package com.udemy.spring.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.udemy.spring.domain.Cliente;
import com.udemy.spring.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
    @Autowired
    private ClienteService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> Listar() {
        List<Cliente> clientes = service.Listar();
        return ResponseEntity.ok().body(clientes);
    }

    @RequestMapping(value = "/paginar", method = RequestMethod.GET)
    public ResponseEntity<Page<Cliente>> Paginar(@RequestParam(value = "pagina", required = false) Integer pagina,
            @RequestParam(value = "linhasPorPagina", required = false) Integer linhasPorPagina,
            @RequestParam(value = "colunaOrdenacao", required = false) String colunaOrdenacao,
            @RequestParam(value = "tipoOrdenacao", required = false) String tipoOrdenacao) {

        Page<Cliente> clientes = service.Paginar(pagina, linhasPorPagina, colunaOrdenacao, tipoOrdenacao);
        return ResponseEntity.ok().body(clientes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> Buscar(@PathVariable Integer id) {
        Cliente cliente = service.Buscar(id);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> Cadastrar(@Valid @RequestBody Cliente cliente) {
        cliente = service.Cadastrar(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.id).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> Atualizar(@PathVariable Integer id, @Valid @RequestBody Cliente cliente) {
        cliente.id = id;
        cliente = service.Atualizar(cliente);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> Apagar(@PathVariable Integer id) {
        service.Apagar(id);
        return ResponseEntity.noContent().build();
    }
}