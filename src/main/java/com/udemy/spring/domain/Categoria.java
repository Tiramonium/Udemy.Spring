package com.udemy.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.validator.constraints.Length;

@Entity
@JsonIdentityInfo(scope = Categoria.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
    public String nome;

    @JsonInclude(Include.NON_EMPTY)
    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY)
    public List<Produto> produtos = new ArrayList<Produto>();

    public Categoria() {}

    public Categoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void Atualizar(Categoria categoria) {
        this.id = categoria.id != null ? categoria.id : this.id;
        this.nome = !Strings.isBlank(categoria.nome) ? categoria.nome : this.nome;
        this.produtos = categoria.produtos != null && !categoria.produtos.isEmpty() ? categoria.produtos : this.produtos;
    }

    public Categoria LazyLoad() {
        this.produtos = new ArrayList<Produto>();
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

        Categoria other = (Categoria) obj;

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