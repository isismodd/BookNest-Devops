package br.com.fiap.demo.controller;

import br.com.fiap.demo.repository.AutorRepository;
import br.com.fiap.demo.repository.LivroRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public HomeController(
            AutorRepository autorRepository,
            LivroRepository livroRepository
    ) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @GetMapping("/")
    public String home(
            Model model,
            Authentication authentication
    ) {

        model.addAttribute(
                "totalAutores",
                autorRepository.count()
        );

        model.addAttribute(
                "totalLivros",
                livroRepository.count()
        );

        model.addAttribute(
                "usuarioLogado",
                authentication.getName()
        );

        return "home";
    }
}