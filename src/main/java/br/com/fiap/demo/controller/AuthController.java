package br.com.fiap.demo.controller;

import br.com.fiap.demo.model.Usuario;
import br.com.fiap.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {

        model.addAttribute("usuario", new Usuario());

        return "auth/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {
            return "auth/cadastro";
        }

        try {

            usuarioService.cadastrar(usuario);

            return "redirect:/login?cadastroSucesso";

        } catch (RuntimeException e) {

            model.addAttribute("erro", e.getMessage());

            return "auth/cadastro";
        }
    }
}