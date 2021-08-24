package com.udemy.spring.repositories;

import java.util.List;
import java.util.Optional;

import com.udemy.spring.domain.Cidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    @Query("SELECT cidade FROM Cidade AS cidade JOIN FETCH cidade.estado WHERE cidade.estado.id = :id")
    public List<Cidade> findAllByEstado(@Param("id") Integer id);

    @Query(value = "SELECT TOP 1 C.* FROM CIDADE AS C INNER JOIN ESTADO AS E ON E.ID = C.ID_ESTADO WHERE C.NOME = ?1", nativeQuery = true)
    public Optional<Cidade> findByNome(@Param("nome") String nome);

    @Query(value = "SELECT TOP 1 C.* FROM CIDADE AS C INNER JOIN ENDERECO AS E ON C.ID = E.ID_CIDADE WHERE E.ID = ?1", nativeQuery = true)
    public Optional<Cidade> findByEndereco(@Param("id") Integer id);
}