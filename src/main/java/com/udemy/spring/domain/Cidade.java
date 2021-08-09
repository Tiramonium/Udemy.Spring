package com.udemy.spring.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer Id;
    public String Nome;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO")
    public Estado Estado;

    public Cidade() {
    }

    public Cidade(Integer id, String nome, Estado estado) {
        this.Id = id;
        this.Nome = nome;
        this.Estado = estado;
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

        Cidade other = (Cidade) obj;

        if (this.Id == null && other.Id != null) {
            return false;
        } else if (!this.Id.equals(other.Id)) {
            return false;
        }

        return true;
    }
}