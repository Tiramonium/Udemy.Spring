package com.udemy.spring.domain;

import java.io.Serializable;

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
}