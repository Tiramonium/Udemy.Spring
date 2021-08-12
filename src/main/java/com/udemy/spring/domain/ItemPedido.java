package com.udemy.spring.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    public ItemPedidoPK id = new ItemPedidoPK();

    public Double desconto;
    public Integer quantidade;
    public Double preco;

    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
        this.id.pedido = pedido;
        this.id.produto = produto;
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    @JsonIgnore
    public Pedido getPedido() {
        return this.id.pedido;
    }

    public Produto getProduto() {
        return this.id.produto;
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

        ItemPedido other = (ItemPedido) obj;

        if (this.id == null && other.id != null) {
            return false;
        } else if (!this.id.equals(other.id)) {
            return false;
        }

        return true;
    }
}