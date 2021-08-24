package com.udemy.spring.repositories;

import java.util.List;
import com.udemy.spring.domain.ItemPedido;
import com.udemy.spring.domain.ItemPedidoPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK> {
    @Query(value = "SELECT IP.* FROM ITEM_PEDIDO AS IP INNER JOIN PEDIDO AS P ON P.ID = IP.ID_PEDIDO WHERE P.ID = ?1", nativeQuery = true)
    public List<ItemPedido> findAllByPedido(@Param("id") Integer id);
}