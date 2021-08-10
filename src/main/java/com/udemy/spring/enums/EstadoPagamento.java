package com.udemy.spring.enums;

public enum EstadoPagamento {
    PENDENTE(1, "Pendente"), QUITADO(2, "Quitado"), CANCELADO(3, "Cancelado");

    private Integer Codigo;
    private String Descricao;

    private EstadoPagamento(Integer codigo, String descricao) {
        this.Codigo = codigo;
        this.Descricao = descricao;
    }

    public Integer getCodigo() {
        return this.Codigo;
    }

    public String getDescricao() {
        return this.Descricao;
    }

    public static EstadoPagamento toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (EstadoPagamento ep : EstadoPagamento.values()) {
            if (codigo.equals(ep.getCodigo())) {
                return ep;
            }
        }

        throw new IllegalArgumentException(String.format("Id inválido: %d", codigo));
    }
}