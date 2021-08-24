package com.udemy.spring.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.udemy.spring.domain.Categoria;
import com.udemy.spring.exceptions.DataIntegrityException;
import com.udemy.spring.exceptions.ObjectNotFoundException;
import com.udemy.spring.helpers.PageRequestHelper;
import com.udemy.spring.repositories.CategoriaRepository;
import com.udemy.spring.repositories.ProdutoRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Logger logger = LogManager.getLogger(CategoriaService.class);

    public List<Categoria> Listar() {
        List<Categoria> categorias = repository.findAll();
        categorias = categorias.stream().map(categoria -> categoria.LazyLoad()).collect(Collectors.toList());
        return categorias;
    }

    public Page<Categoria> Paginar(Integer pagina, Integer linhasPorPagina, String colunaOrdenacao,
            String tipoOrdenacao) {
        PageRequest pageRequest = new PageRequestHelper(pagina, linhasPorPagina, colunaOrdenacao, tipoOrdenacao).Paginate();
        Page<Categoria> categorias = repository.findAll(pageRequest).map(categoria -> categoria.LazyLoad());
        return categorias;
    }

    public Categoria Buscar(Integer id) {
        logger.info(String.format("Buscar: %d", id));
        Optional<Categoria> categoriaResultado = repository.findById(id);

        categoriaResultado.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Categoria.class.getName())));

        categoriaResultado.map(categoria -> categoria.produtos = produtoRepository.findAllByCategoria(categoria.id));
        categoriaResultado.map(categoria -> categoria.produtos.stream().map(produto -> produto.LazyLoad())
                .collect(Collectors.toList()));

        return categoriaResultado.get();
    }

    @Transactional
    public Categoria Cadastrar(Categoria categoria) {
        categoria.id = null;
        return repository.save(categoria);
    }

    @Transactional
    public Categoria Atualizar(Categoria novaCategoria) {
        Categoria categoria = Buscar(novaCategoria.id);
        categoria.Atualizar(novaCategoria);
        return repository.save(categoria);
    }

    public void Apagar(Integer id) {
        Buscar(id);

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException(String.format(
                    "Não é possível excluir o registro porque há entidades relacionadas! Id: %d, Tipo: %s", id,
                    Categoria.class.getName()));
        }
    }
}