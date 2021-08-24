package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@IdClass(ItemPedidoPK.class)
public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    public Double desconto;
    public Integer quantidade;
    public Double preco;

    @Id
    @Valid
    @ManyToOne
    @JsonInclude(Include.NON_NULL)
    @JoinColumn(name = "ID_PEDIDO")
    public Pedido pedido;

    @Id
    @Valid
    @ManyToOne
    @JsonInclude(Include.NON_NULL)
    @JoinColumn(name = "ID_PRODUTO")
    public Produto produto;

    public ItemPedido() {}

    public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
        this.pedido = pedido;
        this.produto = produto;
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public ItemPedido LazyLoad() {
        this.pedido = null;
        this.produto = null;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.pedido == null ? 0 : this.pedido.hashCode());
        result = prime * result + (this.produto == null ? 0 : this.produto.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        {
            return true;
        }

        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        ItemPedido other = (ItemPedido) obj;

        if (this.pedido == null && other.pedido != null)
        {
            return false;
        }
        else if (!Objects.equals(this.pedido, other.pedido))
        {
            return false;
        }

        if (this.produto == null && other.produto != null)
        {
            return false;
        }
        else if (!Objects.equals(this.produto, other.produto))
        {
            return false;
        }

        return true;
    }
}