package com.udemy.spring.domain;

import java.util.Date;

import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.udemy.spring.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoCartao")
@JsonIdentityInfo(scope = PagamentoCartao.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PagamentoCartao extends Pagamento {
    public Integer quantidadeParcelas;

    public PagamentoCartao() {}

    public PagamentoCartao(Integer id, EstadoPagamento estado, Pedido pedido, Date dataPagamento, Integer quantidadeParcelas) {
        super(id, estado, pedido, dataPagamento);
        this.quantidadeParcelas = quantidadeParcelas;
    }
}