package com.udemy.spring.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ItemPedidoPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "ID_PEDIDO")
    public Pedido Pedido;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO")
    public Produto Produto;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.Pedido == null ? 0 : this.Pedido.hashCode());
        result = prime * result + (this.Produto == null ? 0 : this.Produto.hashCode());
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

        ItemPedidoPK other = (ItemPedidoPK) obj;

        if (this.Pedido == null && other.Pedido != null) {
            return false;
        } else if (!this.Pedido.equals(other.Pedido)) {
            return false;
        }

        if (this.Produto == null && other.Produto != null) {
            return false;
        } else if (!this.Produto.equals(other.Produto)) {
            return false;
        }

        return true;
    }
}