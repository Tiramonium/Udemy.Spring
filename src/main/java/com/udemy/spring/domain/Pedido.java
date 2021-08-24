package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(scope = Pedido.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date dataCadastro;

    @Valid
    @ManyToOne
    @JsonInclude(Include.NON_NULL)
    @JoinColumn(name = "ID_CLIENTE")
    public Cliente cliente;

    @Valid
    @ManyToOne
    @JsonInclude(Include.NON_NULL)
    @JoinColumn(name = "ID_ENDERECO")
    public Endereco enderecoEntrega;

    @JsonInclude(Include.NON_EMPTY)
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    public List<@Valid ItemPedido> itens = new ArrayList<ItemPedido>();

    @JsonInclude(Include.NON_EMPTY)
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    public List<@Valid Pagamento> pagamentos = new ArrayList<Pagamento>();

    public Pedido() {}

    public Pedido(Integer id, Date dataCadastro, Cliente cliente, Endereco endereco) {
        this.id = id;
        this.dataCadastro = dataCadastro;
        this.cliente = cliente;
        this.enderecoEntrega = endereco;
    }

    public Pedido LazyLoad() {
        this.cliente = null;
        this.enderecoEntrega = null;
        this.itens = new ArrayList<ItemPedido>();
        this.pagamentos = new ArrayList<Pagamento>();
        return this;
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
        if (this == obj)
        {
            return true;
        }

        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        Pedido other = (Pedido) obj;

        if (this.id == null && other.id != null)
        {
            return false;
        }
        else if (!Objects.equals(this.id, other.id))
        {
            return false;
        }

        return true;
    }
}