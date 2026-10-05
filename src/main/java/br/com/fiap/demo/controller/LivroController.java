package br.com.fiap.demo.controller;

import br.com.fiap.demo.model.Livro;
import br.com.fiap.demo.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    // GET /api/livros
    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    // GET /api/livros/1
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/livros
    @PostMapping
    public ResponseEntity<Livro> criar(@Valid @RequestBody Livro livro) {

        livro.setId(null);

        Livro livroSalvo = livroService.salvar(livro);

        return ResponseEntity
                .created(URI.create("/api/livros/" + livroSalvo.getId()))
                .body(livroSalvo);
    }

    // PUT /api/livros/1
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Livro livro
    ) {
        try {
            return ResponseEntity.ok(
                    livroService.atualizar(id, livro)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/livros/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            livroService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}