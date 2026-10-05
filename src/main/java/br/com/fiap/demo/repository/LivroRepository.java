package br.com.fiap.demo.repository;

import br.com.fiap.demo.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}