package com.udemy.spring;

import java.util.Arrays;

import com.udemy.spring.domain.Categoria;
import com.udemy.spring.domain.Cidade;
import com.udemy.spring.domain.Estado;
import com.udemy.spring.domain.Produto;
import com.udemy.spring.repositories.CategoriaRepository;
import com.udemy.spring.repositories.CidadeRepository;
import com.udemy.spring.repositories.EstadoRepository;
import com.udemy.spring.repositories.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);

        cat1.Produtos.addAll(Arrays.asList(p1, p2, p3));
        cat2.Produtos.addAll(Arrays.asList(p2));

        p1.Categorias.addAll(Arrays.asList(cat1));
        p2.Categorias.addAll(Arrays.asList(cat1, cat2));
        p3.Categorias.addAll(Arrays.asList(cat1));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.Cidades.addAll(Arrays.asList(c1));
        est2.Cidades.addAll(Arrays.asList(c2, c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
    }
}