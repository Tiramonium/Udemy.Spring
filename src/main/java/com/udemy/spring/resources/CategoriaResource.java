package com.udemy.spring.resources;

import java.net.URI;
import java.util.List;

import com.udemy.spring.domain.Categoria;
import com.udemy.spring.services.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
    @Autowired
    private CategoriaService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Categoria> Listar() {
        List<Categoria> categorias = service.Listar();
        return categorias;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> Buscar(@PathVariable Integer id) {
        Categoria categoria = service.Buscar(id);
        return ResponseEntity.ok().body(categoria);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> Cadastrar(@RequestBody Categoria categoria) {
        categoria = service.Cadastrar(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.Id).toUri();
        return ResponseEntity.created(uri).body(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> Atualizar(@PathVariable Integer id, @RequestBody Categoria categoria) {
        categoria.Id = id;
        categoria = service.Atualizar(categoria);
        return ResponseEntity.noContent().build();
    }
}