package com.subastas.api.controllers;

import com.subastas.api.dto.*;
import com.subastas.api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping("/registro/vendedor")
    public ResponseEntity<JwtResponseDTO> registrarVendedor(@Valid @RequestBody RegistroVendedorDTO dto) {
        return ResponseEntity.ok(authService.registrarVendedor(dto));
    }

    @PostMapping("/registro/comprador")
    public ResponseEntity<JwtResponseDTO> registrarComprador(@Valid @RequestBody RegistroCompradorDTO dto) {
        return ResponseEntity.ok(authService.registrarComprador(dto));
    }
} 