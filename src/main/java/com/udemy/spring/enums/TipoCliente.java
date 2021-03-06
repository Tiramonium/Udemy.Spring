package com.udemy.spring.enums;

public enum TipoCliente {
    PESSOAFISICA(1, "Pessoa Física"), PESSOAJURIDICA(2, "Pessoa Jurídica");

    private Integer Codigo;
    private String Descricao;

    private TipoCliente(Integer codigo, String descricao) {
        this.Codigo = codigo;
        this.Descricao = descricao;
    }

    public Integer getCodigo() {
        return this.Codigo;
    }

    public String getDescricao() {
        return this.Descricao;
    }

    public static TipoCliente toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (TipoCliente tc : TipoCliente.values()) {
            if (codigo.equals(tc.getCodigo())) {
                return tc;
            }
        }

        throw new IllegalArgumentException(String.format("Id inválido: %d", codigo));
    }
}