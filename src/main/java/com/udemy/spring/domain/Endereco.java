package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String logradouro;
    public String numero;
    public String complemento;
    public String bairro;
    public String cep;

    @ManyToOne
    @JsonInclude(Include.NON_NULL)
    @JoinColumn(name = "ID_CIDADE")
    public Cidade cidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonInclude(Include.NON_NULL)
    @JoinColumn(name = "ID_CLIENTE")
    public Cliente cliente;

    public Endereco() {
    }

    public Endereco(Integer id, String logradouro, String numero, String complemento, String bairro, String cep,
            Cidade cidade, Cliente cliente) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.cliente = cliente;
    }

    public Endereco LazyLoad(){
        this.cidade = null;
        this.cliente = null;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.id == null ? 0 : this.id.hashCode());
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

        if (this.id == null && other.id != null) {
            return false;
        } else if (!Objects.equals(this.id, other.id)) {
            return false;
        }

        return true;
    }
}