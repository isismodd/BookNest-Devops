package br.com.fiap.demo.controller;

import br.com.fiap.demo.model.Autor;
import br.com.fiap.demo.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/autores")
public class AutorWebController {

    private final AutorService autorService;

    public AutorWebController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "autores",
                autorService.listarTodos()
        );

        return "autores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("autor", new Autor());

        return "autores/formulario.html";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model
    ) {

        Autor autor = autorService.buscarPorId(id)
                .orElseThrow(() ->
                        new RuntimeException("Autor não encontrado")
                );

        model.addAttribute("autor", autor);

        return "autores/formulario.html";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("autor") Autor autor,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return "autores/formulario.html";
        }

        if (autor.getId() == null) {
            autorService.salvar(autor);
        } else {
            autorService.atualizar(
                    autor.getId(),
                    autor
            );
        }

        return "redirect:/autores";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {

        autorService.excluir(id);

        return "redirect:/autores";
    }
}