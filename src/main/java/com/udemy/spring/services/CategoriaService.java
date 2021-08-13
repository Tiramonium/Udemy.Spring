package com.udemy.spring.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.udemy.spring.domain.Categoria;
import com.udemy.spring.exceptions.DataIntegrityException;
import com.udemy.spring.exceptions.ObjectNotFoundException;
import com.udemy.spring.helpers.PaginaHelper;
import com.udemy.spring.repositories.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> Listar() {
        List<Categoria> categorias = repository.findAll();
        categorias = categorias.stream().map(categoria -> new Categoria(categoria)).collect(Collectors.toList());
        return categorias;
    }

    public Page<Categoria> Paginar(Integer pagina, Integer linhasPorPagina, String colunaOrdenacao,
            String tipoOrdenacao) {
        PageRequest pageRequest = new PaginaHelper(pagina, linhasPorPagina, colunaOrdenacao, tipoOrdenacao).Paginar();
        Page<Categoria> categorias = repository.findAll(pageRequest).map(categoria -> new Categoria(categoria));
        return categorias;
    }

    public Categoria Buscar(Integer id) {
        Optional<Categoria> categoria = repository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Categoria.class.getName())));
    }

    public Categoria Cadastrar(Categoria categoria) {
        categoria.id = null;
        return repository.save(categoria);
    }

    public Categoria Atualizar(Categoria categoria) {
        Buscar(categoria.id);
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