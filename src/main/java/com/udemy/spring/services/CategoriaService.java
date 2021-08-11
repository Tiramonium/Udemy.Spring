package com.udemy.spring.services;

import java.util.List;
import java.util.Optional;

import com.udemy.spring.domain.Categoria;
import com.udemy.spring.exceptions.DataIntegrityException;
import com.udemy.spring.exceptions.ObjectNotFoundException;
import com.udemy.spring.repositories.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public Categoria Cadastrar(Categoria categoria) {
        categoria.Id = null;
        return repository.save(categoria);
    }

    public Categoria Atualizar(Categoria categoria) {
        Buscar(categoria.Id);
        return repository.save(categoria);
    }

    public void Apagar(Integer id) {
        Buscar(id);

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException(String.format(
                    "Não é possível excluir o registro pois existem registros dependentes dele! Id: %d, Tipo: %s", id,
                    Categoria.class.getName()));
        }
    }
}