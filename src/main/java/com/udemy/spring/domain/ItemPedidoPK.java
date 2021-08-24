package com.udemy.spring.domain;

import java.io.Serializable;

public class ItemPedidoPK implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer pedido;

    public Integer produto;

    public ItemPedidoPK() {}

    public ItemPedidoPK(Integer pedido, Integer produto) {
        this.pedido = pedido;
        this.produto = produto;
    }
}