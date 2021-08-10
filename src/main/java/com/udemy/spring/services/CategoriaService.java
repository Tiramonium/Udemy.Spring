package com.udemy.spring.services;

import java.util.List;
import java.util.Optional;

import com.udemy.spring.domain.Categoria;
import com.udemy.spring.exceptions.ObjectNotFoundException;
import com.udemy.spring.repositories.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> Listar() {
        List<Categoria> categorias = repository.findAll();
        return categorias;
    }

    public Categoria Buscar(Integer id) {
        Optional<Categoria> categoria = repository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Categoria.class.getName())));
    }
}