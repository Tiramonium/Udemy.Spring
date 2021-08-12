package com.udemy.spring.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.udemy.spring.enums.EstadoPagamento;

@Entity
public class PagamentoBoleto extends Pagamento {
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date dataVencimento;

    public PagamentoBoleto() {
    }

    public PagamentoBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataPagamento, Date dataVencimento) {
        super(id, estado, pedido, dataPagamento);
        this.dataVencimento = dataVencimento;
    }
}