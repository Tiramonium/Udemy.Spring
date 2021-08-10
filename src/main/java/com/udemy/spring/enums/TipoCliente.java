package com.udemy.spring.enums;

public enum TipoCliente {
    PESSOAFISICA(1, "Pessoa Física"), PESSOAJURIDICA(2, "Pessoa Jurídica");

    private Integer Codigo;
    private String Descricao;

    private TipoCliente(Integer codigo, String descricao) {
        this.Codigo = codigo;
        this.Descricao = descricao;
    }

    public Integer GetCodigo() {
        return this.Codigo;
    }

    public String GetDescricao() {
        return this.Descricao;
    }

    public static TipoCliente toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (TipoCliente tc : TipoCliente.values()) {
            if (codigo.equals(tc.GetCodigo())) {
                return tc;
            }
        }

        throw new IllegalArgumentException(String.format("Id inválido: %d", codigo));
    }
}