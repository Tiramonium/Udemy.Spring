package com.udemy.spring.repositories;

import java.util.List;

import com.udemy.spring.domain.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Query(value = "SELECT * FROM PRODUTO AS P INNER JOIN PRODUTO_CATEGORIA AS PC ON P.ID = PC.ID_PRODUTO WHERE PC.ID_CATEGORIA = :id", nativeQuery = true)
    public List<Produto> findAllByCategoria(@Param("id") Integer id);
}