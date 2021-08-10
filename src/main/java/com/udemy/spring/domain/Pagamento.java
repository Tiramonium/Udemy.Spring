package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.spring.enums.EstadoPagamento;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer Id;
    private Integer Estado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date DataPagamento;

    @ManyToOne
    @JoinColumn(name = "ID_PEDIDO")
    @JsonIgnore
    public Pedido Pedido;

    public Pagamento() {
    }

    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido, Date dataPagamento) {
        this.Id = id;
        this.DataPagamento = dataPagamento;
        this.Estado = estado.getCodigo();
        this.Pedido = pedido;
    }

    public EstadoPagamento getEstado() {
        return EstadoPagamento.toEnum(this.Estado);
    }

    public void setEstado(EstadoPagamento estado) {
        this.Estado = estado.getCodigo();
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

        Pagamento other = (Pagamento) obj;

        if (this.Id == null && other.Id != null) {
            return false;
        } else if (!this.Id.equals(other.Id)) {
            return false;
        }

        return true;
    }
}