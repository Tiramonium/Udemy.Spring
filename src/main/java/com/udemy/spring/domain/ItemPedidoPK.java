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
    public Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO")
    public Produto produto;

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
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ItemPedidoPK other = (ItemPedidoPK) obj;

        if (this.pedido == null && other.pedido != null) {
            return false;
        } else if (!this.pedido.equals(other.pedido)) {
            return false;
        }

        if (this.produto == null && other.produto != null) {
            return false;
        } else if (!this.produto.equals(other.produto)) {
            return false;
        }

        return true;
    }
}