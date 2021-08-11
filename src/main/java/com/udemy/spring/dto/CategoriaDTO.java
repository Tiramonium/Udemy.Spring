package com.udemy.spring.dto;

import java.io.Serializable;

import com.udemy.spring.domain.Categoria;

public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer Id;
    public String Nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria categoria) {
        this.Id = categoria.Id;
        this.Nome = categoria.Nome;
    }
}