package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.spring.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String nome;
    public String email;
    public String cpfOuCnpj;
    private Integer tipo;

    @OneToMany(mappedBy = "cliente")
    public List<Endereco> enderecos = new ArrayList<>();

    // @ElementCollection
    // @CollectionTable(name = "TELEFONE")
    // public Set<String> Telefones = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    public List<Telefone> telefones = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    public List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo.getCodigo();
    }

    public TipoCliente getTipo() {
        return TipoCliente.toEnum(this.tipo);
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo.getCodigo();
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

        Cliente other = (Cliente) obj;

        if (this.id == null && other.id != null) {
            return false;
        } else if (!Objects.equals(this.id, other.id)) {
            return false;
        }

        return true;
    }
}