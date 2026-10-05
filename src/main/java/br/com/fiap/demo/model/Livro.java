package br.com.fiap.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "LIVROS")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String titulo;

    @Size(max = 20)
    @Column(length = 20)
    private String isbn;

    @Size(max = 100)
    @Column(length = 100)
    private String genero;

    @Column(name = "ANO_PUBLICACAO")
    private Integer anoPublicacao;

    @ManyToOne
    @JoinColumn(name = "AUTOR_ID", nullable = false)
    private Autor autor;

    public Livro() {
    }

    public Livro(
            Long id,
            String titulo,
            String isbn,
            String genero,
            Integer anoPublicacao,
            Autor autor
    ) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}