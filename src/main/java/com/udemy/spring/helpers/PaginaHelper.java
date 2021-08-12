package com.udemy.spring.helpers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

public class PaginaHelper {
    private Integer Pagina = 0;
    private Integer LinhasPorPagina = 10;
    private String ColunaOrdenacao = "Nome";
    private String TipoOrdenacao = "ASC";

    public PaginaHelper() {
    }

    public PaginaHelper(Integer pagina, Integer linhasPorPagina, String colunaOrdenacao, String tipoOrdenacao) {
        this.Pagina = pagina == null ? this.Pagina : pagina;
        this.LinhasPorPagina = linhasPorPagina == null ? this.LinhasPorPagina : linhasPorPagina;
        this.ColunaOrdenacao = colunaOrdenacao == null ? this.ColunaOrdenacao : colunaOrdenacao;
        this.TipoOrdenacao = tipoOrdenacao == null || (!tipoOrdenacao.equals("ASC") && !tipoOrdenacao.equals("DESC")) ? this.TipoOrdenacao : tipoOrdenacao;
    }

    public PageRequest Paginar() {
        return PageRequest.of(this.Pagina, this.LinhasPorPagina, Direction.valueOf(this.TipoOrdenacao), this.ColunaOrdenacao);
    }
}