package com.udemy.spring.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.udemy.spring.enums.EstadoPagamento;
import org.hibernate.annotations.Formula;

@Entity
@JsonIdentityInfo(scope = Pedido.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date dataCadastro;

    @Formula(value = "(SELECT SUM(COALESCE(IP.sub_total, 0)) FROM Pedido P INNER JOIN Item_Pedido IP WHERE P.id = id)")
    private Double valorTotal;

    @ManyToOne
    @JsonInclude(Include.NON_NULL)
    @JoinColumn(name = "ID_CLIENTE")
    @NotNull(message = "O Cliente deve ser informado")
    public Cliente cliente;

    @ManyToOne
    @JsonInclude(Include.NON_NULL)
    @JoinColumn(name = "ID_ENDERECO")
    @NotNull(message = "O Endereço de Entrega deve ser informado")
    public Endereco enderecoEntrega;

    @JsonInclude(Include.NON_EMPTY)
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    public List<@Valid ItemPedido> itens = new ArrayList<ItemPedido>();

    @JsonInclude(Include.NON_EMPTY)
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    @NotEmpty(message = "Pelo menos um Pagamento deve ser informado")
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        builder.append("Pedido número: ");
        builder.append(this.id);
        builder.append(", Data do Pedido: ");
        builder.append(sdf.format(this.dataCadastro));
        builder.append(", Cliente: ");
        builder.append(this.cliente.nome);
        builder.append(", Situação do Pagamento: ");
        builder.append(this.getPagamento().getEstado().getDescricao());
        builder.append("\nDetalhes:\n");

        for (ItemPedido ip : this.itens)
        {
            builder.append(ip.toString());
        }

        builder.append("Valor total: ");
        builder.append(nf.format(this.getValorTotal()));
        return builder.toString();
    }

    public Double getValorTotal() {
        Double total = 0d;

        for (ItemPedido ip : this.itens)
        {
            total += ip.getSubTotal();
        }

        return total;
    }

    public Pagamento getPagamento() {
        return this.pagamentos.stream().filter(p -> p.getEstado().equals(EstadoPagamento.QUITADO)).findAny()
            .orElse(this.pagamentos.stream().filter(p -> p.getEstado().equals(EstadoPagamento.CANCELADO)).findAny()
                .orElse(this.pagamentos.stream().filter(p -> p.getEstado().equals(EstadoPagamento.PENDENTE)).findAny().orElse(null)));
    }
}