package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Telefone implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    public String telefone;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    @JsonIgnore
    public Cliente cliente;

    public Telefone() {
    }

    public Telefone(String telefone, Cliente cliente) {
        this.telefone = telefone;
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.telefone == null ? 0 : this.telefone.hashCode());
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

        Telefone other = (Telefone) obj;

        if (this.telefone == null && other.telefone != null) {
            return false;
        } else if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }

        return true;
    }
}