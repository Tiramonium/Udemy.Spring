package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.udemy.spring.enums.TipoCliente;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.validator.constraints.Length;

@Entity
@JsonIdentityInfo(scope = Cliente.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho do Cliente deve ser entre {min} e {max} caracteres")
    public String nome;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "E-mail inválido")
    public String email;
    public String cpfOuCnpj;
    private Integer tipo;

    @JsonInclude(Include.NON_EMPTY)
    @NotEmpty(message = "É obrigatório informar pelo menos um Endereço")
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<@Valid Endereco> enderecos = new ArrayList<Endereco>();

    // @ElementCollection
    // @CollectionTable(name = "TELEFONE")
    // public Set<String> Telefones = new HashSet<>();

    @JsonInclude(Include.NON_EMPTY)
    @NotEmpty(message = "É obrigatório informar pelo menos um Telefone")
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<@Valid Telefone> telefones = new ArrayList<Telefone>();

    @JsonInclude(Include.NON_EMPTY)
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    public List<@Valid Pedido> pedidos = new ArrayList<Pedido>();

    public Cliente() {}

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo == null ? null : tipo.getCodigo();
    }

    public Cliente Atualizar(Cliente cliente) {
        this.id = cliente.id != null ? cliente.id : this.id;
        this.nome = !Strings.isBlank(cliente.nome) ? cliente.nome : this.nome;
        this.email = !Strings.isBlank(cliente.email) ? cliente.email : this.email;
        this.tipo = this.tipo == null && cliente.tipo != null ? cliente.tipo : this.tipo;
        this.enderecos = cliente.enderecos != null && !cliente.enderecos.isEmpty()
            ? cliente.enderecos.stream().map(endereco -> new Endereco(endereco.id, endereco.logradouro, endereco.numero, endereco.complemento,
                endereco.bairro, endereco.cep, endereco.cidade, endereco.cliente)).collect(Collectors.toList())
            : this.enderecos;
        this.telefones = cliente.telefones != null && !cliente.telefones.isEmpty()
            ? cliente.telefones.stream().map(telefone -> new Telefone(telefone.telefone, telefone.cliente)).collect(Collectors.toList())
            : this.telefones;
        return this;
    }

    public Cliente LazyLoad() {
        this.enderecos = new ArrayList<Endereco>();
        this.pedidos = new ArrayList<Pedido>();
        this.telefones = new ArrayList<Telefone>();
        return this;
    }

    public TipoCliente getTipo() {
        return TipoCliente.toEnum(this.tipo);
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo.getCodigo();
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

        Cliente other = (Cliente) obj;

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