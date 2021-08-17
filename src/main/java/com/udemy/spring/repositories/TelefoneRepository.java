package com.udemy.spring.repositories;

import java.util.List;

import com.udemy.spring.domain.Telefone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, String> {
    @Query("SELECT telefone FROM Telefone AS telefone JOIN FETCH telefone.cliente WHERE telefone.cliente.id = :id")
    public List<Telefone> findAllByCliente(@Param("id") Integer id);
}