package com.udemy.spring.resources;

import java.util.List;

import com.udemy.spring.domain.Produto;
import com.udemy.spring.helpers.UrlHelper;
import com.udemy.spring.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
    @Autowired
    private ProdutoService service;

    @RequestMapping(value = "/buscar", method = RequestMethod.GET)
    public ResponseEntity<Page<Produto>> Paginar(@RequestParam(value = "nome", required = false) String nome,
        @RequestParam(value = "categorias", required = false) String categorias, @RequestParam(value = "pagina", required = false) Integer pagina,
        @RequestParam(value = "linhasPorPagina", required = false) Integer linhasPorPagina,
        @RequestParam(value = "colunaOrdenacao", required = false) String colunaOrdenacao,
        @RequestParam(value = "tipoOrdenacao", required = false) String tipoOrdenacao) {

        String nomeDecodificado = UrlHelper.UrlDecode(nome);
        List<Integer> categoriaIds = UrlHelper.ListIntDecode(categorias, ",");
        Page<Produto> produtos = service.Buscar(nomeDecodificado, categoriaIds, pagina, linhasPorPagina, colunaOrdenacao, tipoOrdenacao);
        return ResponseEntity.ok().body(produtos);
    }
}