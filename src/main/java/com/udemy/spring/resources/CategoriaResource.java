package com.udemy.spring.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.udemy.spring.domain.Categoria;
import com.udemy.spring.services.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
    @Autowired
    private CategoriaService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Categoria> Listar() {
        Categoria cat1 = new Categoria(1, "Informática");
        Categoria cat2 = new Categoria(2, "Escritório");

        List<Categoria> lista = new ArrayList<>();
        lista.add(cat1);
        lista.add(cat2);

        return lista;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> Buscar(@PathVariable Integer id) {
        Optional<Categoria> categoria = service.Buscar(id);
        return ResponseEntity.ok().body(categoria);
    }
}