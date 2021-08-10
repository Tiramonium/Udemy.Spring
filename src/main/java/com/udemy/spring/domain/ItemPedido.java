package com.udemy.spring.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    public ItemPedidoPK Id = new ItemPedidoPK();

    public Double Desconto;
    public Integer Quantidade;
    public Double Preco;

    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
        this.Id.Pedido = pedido;
        this.Id.Produto = produto;
        this.Desconto = desconto;
        this.Quantidade = quantidade;
        this.Preco = preco;
    }

    public Pedido getPedido() {
        return this.Id.Pedido;
    }

    public Produto getProduto() {
        return this.Id.Produto;
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

        ItemPedido other = (ItemPedido) obj;

        if (this.Id == null && other.Id != null) {
            return false;
        } else if (!this.Id.equals(other.Id)) {
            return false;
        }

        return true;
    }
}