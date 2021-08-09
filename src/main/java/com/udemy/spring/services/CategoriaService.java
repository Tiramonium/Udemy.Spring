package com.udemy.spring.services;

import java.util.Optional;

import com.udemy.spring.domain.Categoria;
import com.udemy.spring.repositories.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public Optional<Categoria> Buscar(Integer id) {
        Optional<Categoria> categoria = repository.findById(id);
        return categoria;
    }
}
