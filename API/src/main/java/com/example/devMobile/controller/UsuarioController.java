package com.example.devMobile.controller;

import com.example.devMobile.dto.LoginRequest;
import com.example.devMobile.entity.Usuario;
import com.example.devMobile.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(
            @RequestBody LoginRequest request
    ) {

        Optional<Usuario> usuario =
                usuarioRepository.findByUsernameAndSenha(
                        request.getUsername(),
                        request.getSenha()
                );

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }

        return ResponseEntity.notFound().build();
    }
}