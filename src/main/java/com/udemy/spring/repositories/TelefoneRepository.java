package com.udemy.spring.repositories;

import com.udemy.spring.domain.Telefone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, String> {

}