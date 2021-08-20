package com.udemy.spring.repositories;

import java.util.Optional;

import com.udemy.spring.domain.Estado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    @Query(value = "SELECT E.* FROM ESTADO AS E WHERE E.NOME = ?1", nativeQuery = true)
    public Optional<Estado> findByNome(@Param("nome") String nome);
}