package com.udemy.spring.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.udemy.spring.enums.EstadoPagamento;

@Entity
public class PagamentoCartao extends Pagamento {
    public Integer quantidadeParcelas;

    public PagamentoCartao() {
    }

    public PagamentoCartao(Integer id, EstadoPagamento estado, Pedido pedido, Date dataPagamento, Integer quantidadeParcelas) {
        super(id, estado, pedido, dataPagamento);
        this.quantidadeParcelas = quantidadeParcelas;
    }
}