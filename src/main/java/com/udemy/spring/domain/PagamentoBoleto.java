package com.udemy.spring.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.udemy.spring.enums.EstadoPagamento;

@Entity
public class PagamentoBoleto extends Pagamento {
    public Date DataVencimento;

    public PagamentoBoleto() {
    }

    public PagamentoBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataPagamento, Date dataVencimento) {
        super(id, estado, pedido, dataPagamento);
        this.DataVencimento = dataVencimento;
    }
}