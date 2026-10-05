package br.com.fiap.demo.service;

import br.com.fiap.demo.model.Autor;
import br.com.fiap.demo.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor atualizar(Long id, Autor autorAtualizado) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        autor.setNome(autorAtualizado.getNome());
        autor.setNacionalidade(autorAtualizado.getNacionalidade());
        autor.setDataNascimento(autorAtualizado.getDataNascimento());

        return autorRepository.save(autor);
    }

    public void excluir(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new RuntimeException("Autor não encontrado");
        }

        autorRepository.deleteById(id);
    }
}