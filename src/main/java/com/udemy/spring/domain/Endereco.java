package com.udemy.spring.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer Id;
    public String Logradouro;
    public String Numero;
    public String Complemento;
    public String Bairro;
    public String CEP;

    @ManyToOne
    @JoinColumn(name = "ID_CIDADE")
    public Cidade Cidade;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    public Cliente Cliente;

    public Endereco() {
    }

    public Endereco(Integer id, String logradouro, String numero, String complemento, String bairro, String cep,
            Cidade cidade, Cliente cliente) {
        this.Id = id;
        this.Logradouro = logradouro;
        this.Numero = numero;
        this.Complemento = complemento;
        this.Bairro = bairro;
        this.CEP = cep;
        this.Cidade = cidade;
        this.Cliente = cliente;
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

        Endereco other = (Endereco) obj;

        if (this.Id == null && other.Id != null) {
            return false;
        } else if (!this.Id.equals(other.Id)) {
            return false;
        }

        return true;
    }
}
