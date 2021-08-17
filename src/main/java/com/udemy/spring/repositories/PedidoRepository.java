package com.udemy.spring.repositories;

import java.util.List;

import com.udemy.spring.domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT pedido FROM Pedido AS pedido JOIN FETCH pedido.cliente WHERE pedido.cliente.id = :id")
    public List<Pedido> findAllByCliente(@Param("id") Integer id);
}