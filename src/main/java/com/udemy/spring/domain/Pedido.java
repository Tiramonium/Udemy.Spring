package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date dataCadastro;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    public Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_ENDERECO")
    public Endereco enderecoEntrega;

    @OneToMany(mappedBy = "id.pedido")
    public List<ItemPedido> itens = new ArrayList<>();

    @OneToMany(mappedBy = "pedido")
    public List<Pagamento> pagamentos = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Integer id, Date dataCadastro, Cliente cliente, Endereco endereco) {
        this.id = id;
        this.dataCadastro = dataCadastro;
        this.cliente = cliente;
        this.enderecoEntrega = endereco;
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

        Pedido other = (Pedido) obj;

        if (this.id == null && other.id != null) {
            return false;
        } else if (!this.id.equals(other.id)) {
            return false;
        }

        return true;
    }
}