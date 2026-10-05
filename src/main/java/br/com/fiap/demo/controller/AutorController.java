package br.com.fiap.demo.controller;

import br.com.fiap.demo.model.Autor;
import br.com.fiap.demo.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // GET /api/autores
    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodos());
    }

    // GET /api/autores/1
    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/autores
    @PostMapping
    public ResponseEntity<Autor> criar(@Valid @RequestBody Autor autor) {

        autor.setId(null);

        Autor autorSalvo = autorService.salvar(autor);

        return ResponseEntity
                .created(URI.create("/api/autores/" + autorSalvo.getId()))
                .body(autorSalvo);
    }

    // PUT /api/autores/1
    @PutMapping("/{id}")
    public ResponseEntity<Autor> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Autor autor
    ) {
        try {
            return ResponseEntity.ok(
                    autorService.atualizar(id, autor)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/autores/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            autorService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}