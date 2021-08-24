package com.udemy.spring.repositories;

import java.util.List;
import java.util.Optional;
import com.udemy.spring.domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT pedido FROM Pedido AS pedido JOIN FETCH pedido.cliente WHERE pedido.cliente.id = :id")
    public List<Pedido> findAllByCliente(@Param("id") Integer id);

    @Query("SELECT pedido, endereco FROM Pedido AS pedido JOIN FETCH pedido.enderecoEntrega AS endereco WHERE pedido.id = :id")
    public Optional<Pedido> findEagerById(@Param("id") Integer id);
}