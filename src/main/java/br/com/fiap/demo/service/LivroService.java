package br.com.fiap.demo.service;

import br.com.fiap.demo.model.Autor;
import br.com.fiap.demo.model.Livro;
import br.com.fiap.demo.repository.AutorRepository;
import br.com.fiap.demo.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroService(
            LivroRepository livroRepository,
            AutorRepository autorRepository
    ) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public Livro salvar(Livro livro) {

        if (livro.getAutor() == null || livro.getAutor().getId() == null) {
            throw new RuntimeException("O autor é obrigatório");
        }

        Autor autor = autorRepository.findById(livro.getAutor().getId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        livro.setAutor(autor);

        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, Livro livroAtualizado) {

        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (
                livroAtualizado.getAutor() == null ||
                        livroAtualizado.getAutor().getId() == null
        ) {
            throw new RuntimeException("O autor é obrigatório");
        }

        Autor autor = autorRepository
                .findById(livroAtualizado.getAutor().getId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setIsbn(livroAtualizado.getIsbn());
        livro.setGenero(livroAtualizado.getGenero());
        livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
        livro.setAutor(autor);

        return livroRepository.save(livro);
    }

    public void excluir(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new RuntimeException("Livro não encontrado");
        }

        livroRepository.deleteById(id);
    }
}