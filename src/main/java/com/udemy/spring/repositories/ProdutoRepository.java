package com.udemy.spring.repositories;

import java.util.List;
import com.udemy.spring.domain.Categoria;
import com.udemy.spring.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Transactional(readOnly = true)
    @Query("SELECT P FROM Produto AS P INNER JOIN P.categorias AS PC WHERE PC.id = :id")
    public List<Produto> findAllByCategoria(@Param("id") Integer id);

    @Transactional(readOnly = true)
    @Query("SELECT P FROM Produto AS P WHERE P.nome LIKE %:nome%")
    public List<Produto> findAllByNome(@Param("nome") String nome);

    @Query("SELECT DISTINCT P FROM Produto AS P INNER JOIN P.categorias AS PC WHERE P.nome LIKE %:nome% AND PC IN :categorias")
    public Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
}