package com.udemy.spring.repositories;

import java.util.List;

import com.udemy.spring.domain.Endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    @Query("SELECT endereco FROM Endereco AS endereco JOIN FETCH endereco.cliente WHERE endereco.cliente.id = :id")
    public List<Endereco> findAllByCliente(@Param("id") Integer id);
}