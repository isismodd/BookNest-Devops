package br.com.fiap.demo.controller;

import br.com.fiap.demo.model.Livro;
import br.com.fiap.demo.service.AutorService;
import br.com.fiap.demo.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livros")
public class LivroWebController {

    private final LivroService livroService;
    private final AutorService autorService;

    public LivroWebController(
            LivroService livroService,
            AutorService autorService
    ) {
        this.livroService = livroService;
        this.autorService = autorService;
    }

    // LISTAR LIVROS
    @GetMapping
    public String listar(Model model) {
        model.addAttribute(
                "livros",
                livroService.listarTodos()
        );

        return "livros/lista";
    }

    // ABRIR FORMULÁRIO DE NOVO LIVRO
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("livro", new Livro());
        model.addAttribute(
                "autores",
                autorService.listarTodos()
        );

        return "livros/formulario";
    }

    // ABRIR FORMULÁRIO DE EDIÇÃO
    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model
    ) {
        Livro livro = livroService.buscarPorId(id)
                .orElseThrow(() ->
                        new RuntimeException("Livro não encontrado")
                );

        model.addAttribute("livro", livro);
        model.addAttribute(
                "autores",
                autorService.listarTodos()
        );

        return "livros/formulario";
    }

    // SALVAR / ATUALIZAR
    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("livro") Livro livro,
            BindingResult result,
            Model model
    ) {

        if (livro.getAutor() == null ||
                livro.getAutor().getId() == null) {

            result.rejectValue(
                    "autor",
                    "autor.obrigatorio",
                    "Selecione um autor."
            );
        }

        if (result.hasErrors()) {
            model.addAttribute(
                    "autores",
                    autorService.listarTodos()
            );

            return "livros/formulario";
        }

        if (livro.getId() == null) {
            livroService.salvar(livro);
        } else {
            livroService.atualizar(
                    livro.getId(),
                    livro
            );
        }

        return "redirect:/livros";
    }

    // EXCLUIR
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        livroService.excluir(id);

        return "redirect:/livros";
    }
}