package com.udemy.spring.services;

import java.util.List;
import com.udemy.spring.domain.Categoria;
import com.udemy.spring.domain.Produto;
import com.udemy.spring.helpers.PageRequestHelper;
import com.udemy.spring.repositories.CategoriaRepository;
import com.udemy.spring.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Page<Produto> Buscar(String nome, List<Integer> ids, Integer pagina, Integer linhasPorPagina, String colunaOrdenacao, String tipoOrdenacao) {
        PageRequest pageRequest = new PageRequestHelper(pagina, linhasPorPagina, colunaOrdenacao, tipoOrdenacao).Paginate();
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        Page<Produto> produtos = repository.search(nome, categorias, pageRequest).map(produto -> produto.LazyLoad());
        return produtos;
    }
}