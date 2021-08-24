package com.udemy.spring.repositories;

import java.util.List;
import com.udemy.spring.domain.Pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    @Query(value = "SELECT PG.* FROM PAGAMENTO AS PG INNER JOIN PEDIDO AS P ON P.ID = PG.ID_PEDIDO WHERE P.ID = ?1", nativeQuery = true)
    public List<Pagamento> findAllByPedido(@Param("id") Integer id);
}