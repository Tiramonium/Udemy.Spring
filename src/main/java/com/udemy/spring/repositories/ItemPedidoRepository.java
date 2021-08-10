package com.udemy.spring.repositories;

import com.udemy.spring.domain.ItemPedido;
import com.udemy.spring.domain.ItemPedidoPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK> {
    
}