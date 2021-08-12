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
    public Integer id;
    private Integer estado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date dataPagamento;

    @ManyToOne
    @JoinColumn(name = "ID_PEDIDO")
    @JsonIgnore
    public Pedido pedido;

    public Pagamento() {
    }

    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido, Date dataPagamento) {
        this.id = id;
        this.dataPagamento = dataPagamento;
        this.estado = estado.getCodigo();
        this.pedido = pedido;
    }

    public EstadoPagamento getEstado() {
        return EstadoPagamento.toEnum(this.estado);
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado.getCodigo();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.id == null ? 0 : this.id.hashCode());
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

        if (this.id == null && other.id != null) {
            return false;
        } else if (!this.id.equals(other.id)) {
            return false;
        }

        return true;
    }
}