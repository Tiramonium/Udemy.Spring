package com.udemy.spring.repositories;

import java.util.List;

import com.udemy.spring.domain.Cidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    @Query("SELECT cidade FROM Cidade AS cidade JOIN FETCH cidade.estado WHERE cidade.estado.id = :id")
    public List<Cidade> findAllByEstado(@Param("id") Integer id);
}