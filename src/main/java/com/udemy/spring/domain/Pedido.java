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
    public Integer Id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date DataCadastro;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    public Cliente Cliente;

    @ManyToOne
    @JoinColumn(name = "ID_ENDERECO")
    public Endereco EnderecoEntrega;

    @OneToMany(mappedBy = "Id.Pedido")
    public List<ItemPedido> Itens = new ArrayList<>();

    @OneToMany(mappedBy = "Pedido")
    public List<Pagamento> Pagamentos = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Integer id, Date dataCadastro, Cliente cliente, Endereco endereco) {
        this.Id = id;
        this.DataCadastro = dataCadastro;
        this.Cliente = cliente;
        this.EnderecoEntrega = endereco;
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

        Pedido other = (Pedido) obj;

        if (this.Id == null && other.Id != null) {
            return false;
        } else if (!this.Id.equals(other.Id)) {
            return false;
        }

        return true;
    }
}
