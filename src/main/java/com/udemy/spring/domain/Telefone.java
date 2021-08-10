package com.udemy.spring.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Telefone {
    @Id
    public String Telefone;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    @JsonIgnore
    public Cliente Cliente;

    public Telefone() {
    }

    public Telefone(String telefone, Cliente cliente) {
        this.Telefone = telefone;
        this.Cliente = cliente;
    }
}