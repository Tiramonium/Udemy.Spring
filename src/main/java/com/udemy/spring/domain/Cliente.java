package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.udemy.spring.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer Id;
    public String Nome;
    public String Email;
    public String CpfOuCnpj;
    private Integer Tipo;

    @OneToMany(mappedBy = "Cliente")
    public List<Endereco> Enderecos = new ArrayList<>();

    // @ElementCollection
    // @CollectionTable(name = "TELEFONE")
    // public Set<String> Telefones = new HashSet<>();

    @OneToMany(mappedBy = "Telefone")
    public List<Telefone> Telefones = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(Integer id, String nome, String email, String cpfoucnpj, TipoCliente tipo) {
        this.Id = id;
        this.Nome = nome;
        this.Email = email;
        this.CpfOuCnpj = cpfoucnpj;
        this.Tipo = tipo.GetCodigo();
    }

    public TipoCliente GetTipo() {
        return TipoCliente.toEnum(this.Tipo);
    }

    public void SetTipo(Integer tipo) {
        this.Tipo = tipo;
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

        Cliente other = (Cliente) obj;

        if (this.Id == null && other.Id != null) {
            return false;
        } else if (!this.Id.equals(other.Id)) {
            return false;
        }

        return true;
    }
}