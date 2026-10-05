package br.com.fiap.demo.repository;

import br.com.fiap.demo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}