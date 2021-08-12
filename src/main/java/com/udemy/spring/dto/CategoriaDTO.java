package com.udemy.spring.dto;

import java.io.Serializable;

import com.udemy.spring.domain.Categoria;

public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer id;
    public String nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.id;
        this.nome = categoria.nome;
    }
}