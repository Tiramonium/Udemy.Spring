package com.udemy.spring.domain;

import java.io.Serializable;

public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer Id;
    public String Nome;

    public Categoria() {
    }

    public Categoria(int id, String nome) {
        this.Id = id;
        this.Nome = nome;
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

        Categoria other = (Categoria) obj;

        if (this.Id == null && other.Id != null) {
            return false;
        } else if (!this.Id.equals(other.Id)) {
            return false;
        }

        return true;
    }
}