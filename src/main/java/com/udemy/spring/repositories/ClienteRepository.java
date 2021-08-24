package com.udemy.spring.repositories;

import java.util.Optional;

import com.udemy.spring.domain.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    @Query(value = "SELECT TOP 1 C.* FROM CLIENTE AS C WHERE C.CPF_OU_CNPJ = ?1", nativeQuery = true)
    public Optional<Cliente> findByCpfCnpj(@Param("cpfcnpj") String cpfOuCnpj);

    @Query(value = "SELECT TOP 1 C.* FROM CLIENTE AS C INNER JOIN PEDIDO AS P ON C.ID = P.ID_CLIENTE WHERE P.ID = ?1", nativeQuery = true)
    public Optional<Cliente> findByPedido(@Param("id") Integer id);
}