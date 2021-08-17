package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String nome;
    public Double preco;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonInclude(Include.NON_EMPTY)
    @JoinTable(name = "PRODUTO_CATEGORIA", joinColumns = @JoinColumn(name = "ID_PRODUTO"), inverseJoinColumns = @JoinColumn(name = "ID_CATEGORIA"))
    public List<Categoria> categorias = new ArrayList<Categoria>();

    @JsonInclude(Include.NON_EMPTY)
    @OneToMany(mappedBy = "id.produto", fetch = FetchType.LAZY)
    public List<ItemPedido> itens = new ArrayList<ItemPedido>();

    public Produto() {
    }

    public Produto(Integer id, String nome, Double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Produto LazyLoad(){
        this.categorias = new ArrayList<Categoria>();
        this.itens = new ArrayList<ItemPedido>();
        return this;
    }

    @JsonIgnore
    public List<Pedido> getPedidos() {
        List<Pedido> lista = new ArrayList<>();

        for (ItemPedido ip : this.itens) {
            lista.add(ip.getPedido());
        }

        return lista;
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

        Produto other = (Produto) obj;

        if (this.id == null && other.id != null) {
            return false;
        } else if (!Objects.equals(this.id, other.id)) {
            return false;
        }

        return true;
    }
}