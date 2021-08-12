package com.udemy.spring.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.udemy.spring.domain.Categoria;
import com.udemy.spring.dto.CategoriaDTO;
import com.udemy.spring.services.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
    @Autowired
    private CategoriaService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> Listar() {
        List<Categoria> categorias = service.Listar();
        List<CategoriaDTO> categoriaDTOs = categorias.stream().map(categoria -> new CategoriaDTO(categoria))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(categoriaDTOs);
    }

    @RequestMapping(value = "/paginar", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> Paginar(@RequestParam(value = "pagina", required = false) Integer pagina,
            @RequestParam(value = "linhasPorPagina", required = false) Integer linhasPorPagina,
            @RequestParam(value = "colunaOrdenacao", required = false) String colunaOrdenacao,
            @RequestParam(value = "tipoOrdenacao", required = false) String tipoOrdenacao) {

        Page<Categoria> categorias = service.Paginar(pagina, linhasPorPagina, colunaOrdenacao, tipoOrdenacao);
        Page<CategoriaDTO> categoriaDTOs = categorias.map(categoria -> new CategoriaDTO(categoria));
        return ResponseEntity.ok().body(categoriaDTOs);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> Buscar(@PathVariable Integer id) {
        Categoria categoria = service.Buscar(id);
        return ResponseEntity.ok().body(categoria);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> Cadastrar(@RequestBody Categoria categoria) {
        categoria = service.Cadastrar(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.id).toUri();
        return ResponseEntity.created(uri).body(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> Atualizar(@PathVariable Integer id, @RequestBody Categoria categoria) {
        categoria.id = id;
        categoria = service.Atualizar(categoria);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> Apagar(@PathVariable Integer id) {
        service.Apagar(id);
        return ResponseEntity.noContent().build();
    }
}