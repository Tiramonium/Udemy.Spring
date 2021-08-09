package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer Id;
    public String Nome;
    public Double Preco;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
        name = "PRODUTO_CATEGORIA",
        joinColumns = @JoinColumn(name = "ID_PRODUTO"),
        inverseJoinColumns = @JoinColumn(name = "ID_CATEGORIA"))
    public List<Categoria> Categorias = new ArrayList<>();

    public Produto() {
    }

    public Produto(Integer id, String nome, Double preco) {
        this.Id = id;
        this.Nome = nome;
        this.Preco = preco;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.Id == null ? 0 : this.Id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Produto other = (Produto) obj;

        if (this.Id == null && other.Id != null) {
            return false;
        } else if (!this.Id.equals(other.Id)) {
            return false;
        }

        return true;
    }
}